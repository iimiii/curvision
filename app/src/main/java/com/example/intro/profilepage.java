package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class profilepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);

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
}