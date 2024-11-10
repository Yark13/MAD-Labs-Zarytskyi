package com.example.traineesofveres.Application.UI.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.Application.UI.MainActivity;
import com.example.traineesofveres.Infrastructure.DatabaseHelper;
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

        _logInBottom = findViewById(R.id.login_activity_loginButton);
        _signUpButton = findViewById(R.id.login_activity_signUpButton);
        _emailTextBox = findViewById(R.id.login_activity_editTextLoginEmail);
        _password = findViewById(R.id.editTextLoginPassword);

        SetBehaviorLogInButton();
        SetBehaviorSignUpButton();
    }

    private  void  SetBehaviorLogInButton(){
        _logInBottom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = _emailTextBox.getText().toString();
                String password = _password.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext() , "Email or password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                TraineeModel accountTrainee;

                try {
                    accountTrainee = _service.Login(email, password);

                    if(accountTrainee == null)
                        Toast.makeText(getApplicationContext() , "Email or password are incorrect", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(INTENT_PARAM_KEY_TRAINEE_ACCOUNT, accountTrainee.Id);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext() , e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void  SetBehaviorSignUpButton(){
        _signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}