package com.example.intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);

        TextView registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);


        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegister.this, registerpage.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegister.this, loginpage.class);
            startActivity(intent);
        });
    }
}
