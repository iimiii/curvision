package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class news_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news1);

        ImageView back1Button = findViewById(R.id.back1);

        back1Button.setOnClickListener(v -> {
            Intent intent = new Intent(news_1.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(news_1.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });
    }
}