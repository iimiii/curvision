package com.example.intro;

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
            startActivity(intent);
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, emergencypage.class);
            startActivity(intent);
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, historypage.class);
            startActivity(intent);
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, vehiclespage.class);
            startActivity(intent);
        });

        avButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, vehiclespage.class);
            startActivity(intent);
        });

        ahButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, historypage.class);
            startActivity(intent);
        });

        ecButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, emergencypage.class);
            startActivity(intent);
        });



    }
}
