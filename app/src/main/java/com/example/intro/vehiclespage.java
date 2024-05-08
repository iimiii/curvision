package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
public class vehiclespage extends AppCompatActivity {

    private static final long DATA_EXPIRATION_TIME = 3 * 60 * 1000; // 3 minutes in milliseconds
    private static long lastUpdateTime = 0;
    private DatabaseReference leftSensorRef;
    private DatabaseReference rightSensorRef;

    private LinearLayout leftLaneLinearLayout1;
    private LinearLayout rightLaneLinearLayout1;
    private LinearLayout leftLaneLinearLayout2;
    private LinearLayout rightLaneLinearLayout2;

    private final LinkedList<String> leftLaneData = new LinkedList<>();
    private final LinkedList<String> rightLaneData = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehiclespage);

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);
        leftLaneLinearLayout1 = findViewById(R.id.left_lane_linear_layout);
        rightLaneLinearLayout1 = findViewById(R.id.right_lane_linear_layout);
        leftLaneLinearLayout2 = findViewById(R.id.left_lane_linear_layout2);
        rightLaneLinearLayout2 = findViewById(R.id.right_lane_linear_layout2);

        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(vehiclespage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(vehiclespage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(vehiclespage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(vehiclespage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(vehiclespage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        leftSensorRef = database.getReference("LeftSensor/speed");
        rightSensorRef = database.getReference("RightSensor/speed");

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > DATA_EXPIRATION_TIME) {
            leftLaneData.clear();
            rightLaneData.clear();
        } else {
            // Refresh the data on the UI if it is still valid
            updateLaneTextViews(leftLaneLinearLayout1, leftLaneData);
            updateLaneTextViews(rightLaneLinearLayout1, rightLaneData);
            updateLaneTextViews(leftLaneLinearLayout2, leftLaneData);
            updateLaneTextViews(rightLaneLinearLayout2, rightLaneData);
        }

        attachDatabaseReadListeners();
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(vehiclespage.this, cls);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(
                vehiclespage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
        startActivity(intent, options.toBundle());
    }

    private void attachDatabaseReadListeners() {
        leftSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float speed = dataSnapshot.getValue(Float.class);
                if (speed != null && speed >= 1.00f) {
                    String formattedSpeed = String.format("%.2f m/s", speed);
                    leftLaneData.addFirst(formattedSpeed);
                    updateLaneTextViews(leftLaneLinearLayout1, leftLaneData);
                    updateLaneTextViews(rightLaneLinearLayout2, leftLaneData);
                    lastUpdateTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible cancellations
            }
        });

        rightSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float speed = dataSnapshot.getValue(Float.class);
                if (speed != null && speed >= 1.00f) {
                    String formattedSpeed = String.format("%.2f m/s", speed);
                    rightLaneData.addFirst(formattedSpeed);
                    updateLaneTextViews(rightLaneLinearLayout1, rightLaneData);
                    updateLaneTextViews(leftLaneLinearLayout2, rightLaneData);
                    lastUpdateTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible cancellations
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > DATA_EXPIRATION_TIME) {
            leftLaneData.clear();
            rightLaneData.clear();
        }
        // Refresh UI
        updateLaneTextViews(leftLaneLinearLayout1, leftLaneData);
        updateLaneTextViews(rightLaneLinearLayout1, rightLaneData);
        updateLaneTextViews(leftLaneLinearLayout2, leftLaneData);
        updateLaneTextViews(rightLaneLinearLayout2, rightLaneData);
    }

    private void updateLaneTextViews(LinearLayout layout, LinkedList<String> data) {
        layout.removeAllViews();
        for (String speed : data) {
            TextView textView = new TextView(this);
            textView.setText(speed);
            textView.setBackgroundResource(R.drawable.frame2);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(12, 12, 0, 12);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTypeface(null, Typeface.BOLD);
            layout.addView(textView);
        }
    }
}