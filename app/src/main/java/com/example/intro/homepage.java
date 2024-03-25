package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        TextView avButton = findViewById(R.id.av);
        TextView ahButton = findViewById(R.id.ah);
        TextView ecButton = findViewById(R.id.ec);


        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        avButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });

        ahButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });

        ecButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
            startActivity(intent, options.toBundle());
        });


    }

}
