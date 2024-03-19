package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class registerpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);

        Button sign_upButton = findViewById(R.id.sign_up);
        TextView already_have_an_accountButton = findViewById(R.id.already_have_an_account);


        sign_upButton.setOnClickListener(v -> {
            Intent intent = new Intent(registerpage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(registerpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });


        already_have_an_accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(registerpage.this, loginpage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(registerpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });

    }
}