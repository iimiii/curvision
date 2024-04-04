package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class news_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news4);


        ImageView back4Button = findViewById(R.id.back4);

        back4Button.setOnClickListener(v -> {
            Intent intent = new Intent(news_4.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(news_4.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });
    }
}