package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to homepage
            navigateToHomePage();
        } else {
            // User is not signed in, navigate to intro layout
            navigateToIntroLayout();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToHomePage();
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(MainActivity.this, homepage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
        finish(); // Finish MainActivity to prevent returning to it when back button is pressed
    }

    private void navigateToIntroLayout() {
        Intent intent = new Intent(MainActivity.this, Introduction.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
        finish(); // Finish MainActivity to prevent returning to it when back button is pressed
    }
}