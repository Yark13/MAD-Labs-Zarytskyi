package com.example.traineesofveres.Application.UI.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.Application.UI.MainActivity;
import com.example.traineesofveres.R;
import com.example.traineesofveres.Application.UI.signup.SignUpActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    public static final String INTENT_PARAM_KEY_TRAINEE_ACCOUNT = "INTENT_PARAM_KEY_TRAINEE_ACCOUNT";

    @Inject
    ITraineeService _service;

    private Button _logInBottom, _signUpButton;
    private EditText _emailTextBox;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(AutoLogin()) return;

        FindingViewElements();

        SetBehaviorLogInButton();
        SetBehaviorSignUpButton();
    }

    private Boolean AutoLogin(){
        if (isUserLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(INTENT_PARAM_KEY_TRAINEE_ACCOUNT, getUserId());
            startActivity(intent);
            finish();

            return true;
        }

        return false;
    }

    private void FindingViewElements(){
        _logInBottom = findViewById(R.id.login_activity_loginButton);
        _signUpButton = findViewById(R.id.login_activity_signUpButton);
        _emailTextBox = findViewById(R.id.login_activity_editTextLoginEmail);
        _password = findViewById(R.id.editTextLoginPassword);
    }

    private  void  SetBehaviorLogInButton(){
        _logInBottom.setOnClickListener(view -> {
            String email = _emailTextBox.getText().toString();
            String password = _password.getText().toString();

            try {

                if(email.isEmpty() || password.isEmpty()) throw new Exception("Email or password cannot be empty");

                if(!_service.IsConnection()){
                    new AlertDialog.Builder(this)
                            .setTitle("Exception access to Database")
                            .setMessage("Cannot use database, no connection!")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                    return;
                }

                TraineeModel accountTrainee;
                accountTrainee = _service.Login(email, password);

                if(accountTrainee == null) throw new Exception("Email or password are incorrect");

                SaveUserData(accountTrainee.Id);

                new AlertDialog.Builder(this)
                        .setTitle("Welcome back my little trainee")
                        .setMessage("I have been waiting for you for so long, " + accountTrainee.Name + " " + accountTrainee.Surname)
                        .setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(INTENT_PARAM_KEY_TRAINEE_ACCOUNT, accountTrainee.Id);
                            startActivity(intent);
                            finish();
                        })
                        .show();
            }
            catch (Exception e){
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage(e.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    private  void  SetBehaviorSignUpButton(){
        _signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private boolean isUserLoggedIn() {
        return getUserId() != -1;
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.UserPrefs, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(MainActivity.UserIdPref, -1);
    }

    private void SaveUserData(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.UserPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MainActivity.UserIdPref, userId);
        editor.apply();
    }
}