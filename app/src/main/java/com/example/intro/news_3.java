package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class news_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news3);

        ImageView back3Button = findViewById(R.id.back3);

        back3Button.setOnClickListener(v -> {
            Intent intent = new Intent(news_3.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(news_3.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

    }
}