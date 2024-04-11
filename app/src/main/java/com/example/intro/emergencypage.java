package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class emergencypage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergencypage);

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);


        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });


        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });



    }
}
