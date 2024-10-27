package com.example.traineesofveres.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.traineesofveres.MainActivity;
import com.example.traineesofveres.R;
import com.example.traineesofveres.ui.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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