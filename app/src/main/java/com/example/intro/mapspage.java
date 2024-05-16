package com.example.intro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class mapspage extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private PopupWindow popupWindow;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private TextToSpeech textToSpeech;
    private boolean notificationSpoken = false;
    private GoogleMap mMap;

    private final LatLng[] geofences = {
            new LatLng(10.796362, 123.994095),
    };
    private final float geofenceRadius = 400f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapspage);

        MapView mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        // Check for Google Play Services availability
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(this, "Google Play Services not available", Toast.LENGTH_SHORT).show();
            finish();
        }

        initPopupWindow();
        initButtons();
        initLocationServices();
        initTextToSpeech();
    }

    private void initPopupWindow() {
        @SuppressLint("InflateParams")
        View popupView = LayoutInflater.from(this).inflate(R.layout.info_loc, null);
        int width = 700;
        int height = 800;
        popupView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }

    private void initButtons() {
        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);

        ImageView infolocationButton = findViewById(R.id.infolocation);
        infolocationButton.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.showAtLocation(v, Gravity.TOP, 50, 50);
            }
        });

        home_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(mapspage.this, homepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mapspage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        history_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(mapspage.this, historypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mapspage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        car_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(mapspage.this, vehiclespage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mapspage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        emergency_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(mapspage.this, emergencypage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mapspage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });

        prof_iconButton.setOnClickListener(v -> {
            Intent intent = new Intent(mapspage.this, profilepage.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(mapspage.this, R.anim.animate_fade_enter, R.anim.animate_fade_exit);
            startActivity(intent, options.toBundle());
        });
    }

    private void initLocationServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        for (LatLng geofence : geofences) {
                            float[] distance = new float[1];
                            Location.distanceBetween(location.getLatitude(), location.getLongitude(), geofence.latitude, geofence.longitude, distance);
                            if (distance[0] <= geofenceRadius && !notificationSpoken) {
                                displayWarningNotification();
                                String message = "WARNING! You're Entering a Blind Curve Area";
                                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                                notificationSpoken = true;
                            } else if (distance[0] > geofenceRadius) {
                                notificationSpoken = false;
                            }
                        }
                    }
                }
            }
        };
    }

    private void displayWarningNotification() {
        // Display a warning notification using a custom Toast
        View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 175);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            } else {
                Toast.makeText(mapspage.this, "Text to Speech initialization failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable user's location and zoom to current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Add markers and circles for each geofence
        for (LatLng geofence : geofences) {
            mMap.addMarker(new MarkerOptions().position(geofence).title("Geofence Area"));
            mMap.addCircle(new CircleOptions()
                    .center(geofence)
                    .radius(geofenceRadius)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(64, 200, 0, 0)));
        }

        // Move the camera to the first geofence
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geofences[0], 15f));
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        return resultCode == ConnectionResult.SUCCESS;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MapView mMapView = findViewById(R.id.map);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
