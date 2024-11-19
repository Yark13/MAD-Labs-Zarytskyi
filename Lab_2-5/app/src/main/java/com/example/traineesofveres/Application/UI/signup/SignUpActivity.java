package com.example.traineesofveres.Application.UI.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.traineesofveres.DTO.Domain.TraineeModel;
import com.example.traineesofveres.Domain.Services.TraineeService.ITraineeService;
import com.example.traineesofveres.R;
import com.example.traineesofveres.Application.UI.login.LoginActivity;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.zip.DataFormatException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {

    @Inject
    ITraineeService _service;

    private EditText _editTextName, _editTextSurname, _editTextEmail, _editTextAge, _editTextPassword, _editTextAgainPassword;
    private Button _signUpButton, _backToLoginActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FindingViewElements();

        SetBehaviorSignUpButton();
        SetBehaviorBackToLoginActivityButton();
    }

    private void FindingViewElements(){
        _editTextName = findViewById(R.id.editTextSignUpName);
        _editTextSurname = findViewById(R.id.editTextSignUpSurname);
        _editTextEmail = findViewById(R.id.editTextSignUpEmail);
        _editTextAge = findViewById(R.id.editTextSignUpAge);
        _editTextPassword = findViewById(R.id.editTextSignUpPassword);
        _editTextAgainPassword = findViewById(R.id.editTextSignUpAgainPassword);

        _signUpButton = findViewById(R.id.signUp_activity_signUpButton);
        _backToLoginActivityButton = findViewById(R.id.signUp_activity_BackButton);
    }

    private  void  SetBehaviorSignUpButton(){
        _signUpButton.setOnClickListener(view -> {
            try {

                if(!_service.IsConnection()){
                    new AlertDialog.Builder(this)
                            .setTitle("Exception access to Database")
                            .setMessage("Cannot use database, no connection!")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                    return;
                }


                TraineeModel newAccount = GetNewAccountFromView();

                ValidateModel(newAccount);

                if(!SignUp(newAccount)) throw new Exception("Something wrong");

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("Success!!")
                        .setMessage("Welcome to our little family, new trainee\uD83D\uDC49\uD83D\uDC48\uD83E\uDD17")
                        .setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .show();
            }
            catch (Exception e){
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("Error")
                        .setMessage(e.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    private  void  SetBehaviorBackToLoginActivityButton(){
        _backToLoginActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private TraineeModel GetNewAccountFromView(){
        TraineeModel newAccount = new TraineeModel();

        String password = _editTextPassword.getText().toString().trim();
        String passwordAgain = _editTextAgainPassword.getText().toString().trim();
        String stringAge = _editTextAge.getText().toString().trim();

        if(!password.equals(passwordAgain))
            throw new InvalidParameterException("Passwords are not equal!");

        newAccount.Name = _editTextName.getText().toString().trim();
        newAccount.Surname = _editTextSurname.getText().toString().trim();
        newAccount.Email = _editTextEmail.getText().toString().trim();
        newAccount.Password = password;
        newAccount.Age = stringAge.isEmpty()? 0: Integer.parseInt(stringAge);

        return newAccount;
    }

    private void ValidateModel(TraineeModel model){
        String validateMessage = "";

        if(model.Name.isEmpty()) validateMessage+="Name is required\n";
        if(model.Surname.isEmpty()) validateMessage+="Surname is required\n";

        if(model.Email.isEmpty()) validateMessage+="Email is required\n";
            else if(!_service.IsValidEmail(model.Email))   validateMessage+="Invalid email\n";

        if(model.Password.isEmpty()) validateMessage+="Password is required\n";
            else if (model.Password.length() < 8) validateMessage+="Password must be longer than 8 signs\n";

        if(model.Age == 0) validateMessage+="Age is required\n";
            else if(model.Age <= 16 || model.Age >= 100) validateMessage+="Age must be from 16 to 100 y.o.\n";

        if(!validateMessage.isEmpty()) throw new InvalidParameterException(validateMessage);
    }

    private Boolean SignUp(TraineeModel newTrainee){
        try {
            Objects.requireNonNull(newTrainee);

            TraineeModel result = _service.SignUp(newTrainee);
        }
        catch (Exception e){
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("Error")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();

            return false;
        }

        return true;
    }
}