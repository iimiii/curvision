package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class historypage extends AppCompatActivity {

    private HorizontalScrollView horizontalScrollView;
    private final int scrollSpeed = 2; // Adjust scroll speed here
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historypage);

        horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        handler = new Handler();

        // Start auto-scrolling
        autoScroll();

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);
        ImageView history1Button = findViewById(R.id.history1);
        ImageView history2Button = findViewById(R.id.history2);
        ImageView history3Button = findViewById(R.id.history3);
        ImageView history4Button = findViewById(R.id.history4);


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


        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history1Button.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, news_1.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history2Button.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, news_2.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history3Button.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, news_3.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history4Button.setOnClickListener(v -> {
            Intent intent = new Intent(historypage.this, news_4.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(historypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

    }
    private void autoScroll() {
        handler.postDelayed(() -> {

            int scrollX = horizontalScrollView.getScrollX() + scrollSpeed;


            if (scrollX >= (horizontalScrollView.getChildAt(0).getWidth() - horizontalScrollView.getWidth())) {
                scrollX = 0;
            }


            horizontalScrollView.smoothScrollTo(scrollX, 0);


            autoScroll();
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
