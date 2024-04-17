package com.example.intro;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

public class emergencypage extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergencypage);

        ImageView callButton = findViewById(R.id.callButton);

        // Set OnClickListener to show the popup menu
        callButton.setOnClickListener(v -> {
            Log.d("OnClickListener", "Call button clicked");
            PopupMenu popupMenu = new PopupMenu(emergencypage.this, callButton);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> onMenuItemClick(item.getItemId()));
            popupMenu.show();
        });


        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView loc_iconButton = findViewById(R.id.loc_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);


        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });


        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        loc_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, mapspage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(emergencypage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(emergencypage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

    }

    private boolean onMenuItemClick(int itemId) {
        if (itemId == R.id.menu_911) {
            callEmergencyNumber("911");
            return true;
        } else if (itemId == R.id.menu_custom) {
            callEmergencyNumber("09958736046");
            return true;
        } else {
            return false;
        }
    }



    private void callEmergencyNumber(String number) {
        // Intent to directly call the specified number
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));

        // Check if the CALL_PHONE permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Start the call if permission is granted
            startActivity(intent);
        } else {
            // Request the CALL_PHONE permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                callEmergencyNumber("911");
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied to make a phone call", Toast.LENGTH_SHORT).show();
            }
        }
    }
}