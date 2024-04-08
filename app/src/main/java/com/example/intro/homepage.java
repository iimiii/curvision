package com.example.intro;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;



import androidx.appcompat.app.AppCompatActivity;



public class homepage extends AppCompatActivity {

    private HorizontalScrollView horizontalScrollView;
    private final int scrollSpeed = 2; // Adjust scroll speed here
    private Handler handler;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        handler = new Handler();

        autoScroll();

        initPopupWindow();

        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);
        ImageView haButton = findViewById(R.id.ha);
        ImageView ecButton = findViewById(R.id.ec);
        ImageView vaButton = findViewById(R.id.va);
        ImageView news1Button = findViewById(R.id.news1);
        ImageView news2Button = findViewById(R.id.news2);
        ImageView news3Button = findViewById(R.id.news3);
        ImageView news4Button = findViewById(R.id.news4);

        ImageView notification_iconButton = findViewById(R.id.notification_icon);
        notification_iconButton.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.showAtLocation(v, Gravity.TOP, 0, 0);
            }
        });


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

    private void initPopupWindow() {
        @SuppressLint("InflateParams")
        View popupView = LayoutInflater.from(this).inflate(R.layout.notif_popup, null);
        int width = 900;
        int height = 200;
        popupView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
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
