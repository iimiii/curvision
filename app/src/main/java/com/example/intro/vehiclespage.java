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

    private DatabaseReference leftSensorRef;
    private DatabaseReference rightSensorRef;

    private LinearLayout leftLaneLinearLayout;
    private LinearLayout rightLaneLinearLayout;

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
        leftLaneLinearLayout = findViewById(R.id.left_lane_linear_layout);
        rightLaneLinearLayout = findViewById(R.id.right_lane_linear_layout);

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

        // Attach ValueEventListener to listen for changes in speed for Left Sensor
        leftSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float speed = dataSnapshot.getValue(Float.class);
                if (!Float.isNaN(speed) && speed >= 1.00f) {
                    String formattedSpeed = String.format("%.2f m/s", speed);
                    leftLaneData.addFirst(formattedSpeed);
                    updateLeftLaneTextViews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
            }
        });

        // Attach ValueEventListener to listen for changes in speed for Right Sensor
        rightSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float speed = dataSnapshot.getValue(Float.class);
                if (!Float.isNaN(speed) && speed >= 1.00f) {
                    String formattedSpeed = String.format("%.2f m/s", speed);
                    rightLaneData.addFirst(formattedSpeed);
                    updateRightLaneTextViews();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
            }
        });
    }

    private void updateLeftLaneTextViews() {
        leftLaneLinearLayout.removeAllViews();
        for (String speed : leftLaneData) {
            TextView textView = new TextView(this);
            textView.setText(speed);
            textView.setBackgroundResource(R.drawable.frame2); // Set background drawable
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textView.setPadding(12, 12, 0, 12); // Add padding for better appearance
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP); // Center the text
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTypeface(null, Typeface.BOLD);
            leftLaneLinearLayout.addView(textView);
        }
    }

    private void updateRightLaneTextViews() {
        rightLaneLinearLayout.removeAllViews();
        for (String speed : rightLaneData) {
            TextView textView = new TextView(this);
            textView.setText(speed);
            textView.setBackgroundResource(R.drawable.frame2); // Set background drawable
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textView.setPadding(12, 12, 0, 12); // Add padding for better appearance
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTypeface(null, Typeface.BOLD);
            rightLaneLinearLayout.addView(textView);
        }
    }
}