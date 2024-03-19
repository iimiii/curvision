package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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



        sign_inButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, homepage.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
                startActivity(intent, options.toBundle());
        });

        forgot_passwordButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, registerpage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });

        create_new_accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(loginpage.this, registerpage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });

    }
}