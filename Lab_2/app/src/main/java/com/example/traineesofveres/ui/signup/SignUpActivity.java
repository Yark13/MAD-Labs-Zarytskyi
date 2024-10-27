package com.example.traineesofveres.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.traineesofveres.R;
import com.example.traineesofveres.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    private Button _signUpButton;
    private Button _backToLoginActivityButton;

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

        _signUpButton = findViewById(R.id.signUp_activity_signUpButton);
        _backToLoginActivityButton = findViewById(R.id.signUp_activity_BackButton);

        SetBehaviorSignUpButton();
        SetBehaviorBackToLoginActivityButton();
    }

    private  void  SetBehaviorSignUpButton(){
        _signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
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
}