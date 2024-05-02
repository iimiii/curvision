package com.example.intro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
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
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class mapspage extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private GeofencingClient mGeofencingClient;
    private List<Geofence> mGeofenceList;
    private PendingIntent geofencePendingIntent; // Declare here
    private PopupWindow popupWindow;

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

        mGeofenceList = new ArrayList<>();
        mGeofencingClient = LocationServices.getGeofencingClient(this);

        // Define curve road geofence parameters
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("CurveRoad")
                .setCircularRegion(10.320109, 123.929121, 30)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        initPopupWindow();

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Enable user's location and zoom to current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Add marker for the curve road
        LatLng curveRoadLatLng = new LatLng(10.320109, 123.929121);
        googleMap.addMarker(new MarkerOptions().position(curveRoadLatLng).title("Curve Road"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curveRoadLatLng, 15f));

        LatLng geofenceCenter = new LatLng(10.320109, 123.929121); // Example coordinates
        float radius = 30; // in meters
        googleMap.addCircle(new CircleOptions()
                .center(geofenceCenter)
                .radius(radius)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(64, 200, 0, 0)));

        // Register geofences
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                float[] distance = new float[1];
                Location.distanceBetween(geofenceCenter.latitude, geofenceCenter.longitude, latLng.latitude, latLng.longitude, distance);
                if (distance[0] <= radius) {
                    // Inside geofence
                    sendGeofenceTransitionBroadcast(Geofence.GEOFENCE_TRANSITION_ENTER, "CurveRoad");
                } else {
                    // Outside geofence
                    sendGeofenceTransitionBroadcast(Geofence.GEOFENCE_TRANSITION_EXIT, "CurveRoad");
                }
            }
        });
    }

    // Inside onMapReady method
    private void sendGeofenceTransitionBroadcast(int transitionType, String geofenceId) {
        Intent intent = new Intent("GEOFENCE_TRANSITION");
        intent.putExtra("transitionType", transitionType);
        intent.putExtra("geofenceId", geofenceId);
        sendBroadcast(intent);
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, broadcastreceiver.class);
        intent.setAction("GEOFENCE_TRANSITION");
        intent.putExtra("transitionType", Geofence.GEOFENCE_TRANSITION_ENTER); // Example transition type
        intent.putExtra("geofenceId", "CurveRoad"); // Example geofence ID
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, register geofences
                registerGeofences();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapView mMapView = findViewById(R.id.map);
        mMapView.onDestroy();
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

    private void registerGeofences() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
        }
    }
}