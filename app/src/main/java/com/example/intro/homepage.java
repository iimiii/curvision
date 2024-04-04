package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.HorizontalScrollView;



import androidx.appcompat.app.AppCompatActivity;



public class homepage extends AppCompatActivity {

    private HorizontalScrollView horizontalScrollView;
    private final int scrollSpeed = 2; // Adjust scroll speed here
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        handler = new Handler();

        autoScroll();

        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);
        ImageView haButton = findViewById(R.id.ha);
        ImageView ecButton = findViewById(R.id.ec);
        ImageView vaButton = findViewById(R.id.va);
        ImageView news1Button = findViewById(R.id.news1);
        ImageView news2Button = findViewById(R.id.news2);
        ImageView news3Button = findViewById(R.id.news3);
        ImageView news4Button = findViewById(R.id.news4);



        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        haButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        ecButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        vaButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        news1Button.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, news_1.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        news2Button.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, news_2.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        news3Button.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, news_3.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        news4Button.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, news_4.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(homepage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
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
