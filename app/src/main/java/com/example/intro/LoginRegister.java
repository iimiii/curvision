package com.example.intro;

import android.app.ActivityOptions;
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
            ActivityOptions options = ActivityOptions.makeCustomAnimation(LoginRegister.this, R.anim.animate_zoom_enter, R.anim.animate_zoom_exit);
            startActivity(intent, options.toBundle());
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegister.this, loginpage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(LoginRegister.this, R.anim.animate_zoom_enter, R.anim.animate_zoom_exit);
            startActivity(intent, options.toBundle());
        });
    }

    @Override
    public void onBackPressed() {
        // Check if the user is already on the homepage
        if (isUserOnHomepage()) {
            // Do nothing or exit the app
            // To exit the app, uncomment the line below and remove the super.onBackPressed() call
            // finishAffinity();
        } else {
            super.onBackPressed();
        }
    }

    // Method to check if the user is already on the homepage
    private boolean isUserOnHomepage() {
        // Implement the logic to check if the user is on the homepage
        // For example, you can check the current activity or any other condition
        // Return true if the user is on the homepage, false otherwise
        return false; // Placeholder condition, replace with your actual logic
    }
}