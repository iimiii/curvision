package com.example.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class loginpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);


        Button sign_inButton = findViewById(R.id.sign_in);
        TextView forgot_passwordButton = findViewById(R.id.forgot_password);
        TextView create_new_accountButton = findViewById(R.id.create_new_account);

        ImageView googleBtn = findViewById(R.id.googleBtn);

        ImageView facebookBtn = findViewById(R.id.facebookBtn);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        sign_inButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, homepage.class);
            startActivity(intent);
        });

        forgot_passwordButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, registerpage.class);
            startActivity(intent);
        });

        create_new_accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, registerpage.class);
            startActivity(intent);
        });

    }
}