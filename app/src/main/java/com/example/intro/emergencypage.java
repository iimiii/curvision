package com.example.intro;

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
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);


        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, homepage.class);
            startActivity(intent);
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, emergencypage.class);
            startActivity(intent);
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, historypage.class);
            startActivity(intent);
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, vehiclespage.class);
            startActivity(intent);
        });



    }
}
