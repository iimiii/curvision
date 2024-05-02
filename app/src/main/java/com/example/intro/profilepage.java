package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class profilepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);

        setupNavigationListeners();

        // Load profile info from SharedPreferences
        loadProfileInfo();

        // Check if there's new profile data from the intent (e.g., after signing in)
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            String name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String photoUrl = intent.getStringExtra("photoUrl");

            // Save the new profile data to SharedPreferences
            saveProfileInfo(name, email, photoUrl);

            // Update the profile views with the new data
            updateProfileViews(name, email, photoUrl);
        }
    }

    private void setupNavigationListeners() {
        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView loc_iconButton = findViewById(R.id.loc_icon);

        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(profilepage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(profilepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(profilepage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(profilepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(profilepage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(profilepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(profilepage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(profilepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(profilepage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(profilepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });
    }

    private void saveProfileInfo(String name, String email, String photoUrl) {
        // Save profile info to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("photoUrl", photoUrl);
        editor.apply();
    }

    private void loadProfileInfo() {
        // Load profile info from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String photoUrl = prefs.getString("photoUrl", "");

        TextView nameTextView = findViewById(R.id.profile_name);
        TextView emailTextView = findViewById(R.id.profile_email);
        ImageView profileImageView = findViewById(R.id.profile_image);

        nameTextView.setText(name);
        emailTextView.setText(email);
        Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .circleCrop()
                .into(profileImageView);
    }

    private void updateProfileViews(String name, String email, String photoUrl) {
        // Update profile views with provided data
        TextView nameTextView = findViewById(R.id.profile_name);
        TextView emailTextView = findViewById(R.id.profile_email);
        ImageView profileImageView = findViewById(R.id.profile_image);

        nameTextView.setText(name);
        emailTextView.setText(email);

        Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .circleCrop()
                .into(profileImageView);
    }
}
