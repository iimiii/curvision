package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class historypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historypage);

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);


        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

    }
}
