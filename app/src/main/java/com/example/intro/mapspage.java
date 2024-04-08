package com.example.intro;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
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
                .setCircularRegion(10.796488501166039, 123.99457851460627, 10)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        ImageView home_iconButton = findViewById(R.id.home_icon);
        ImageView emergency_iconButton = findViewById(R.id.emergency_icon);
        ImageView history_iconButton = findViewById(R.id.history_icon);
        ImageView car_iconButton = findViewById(R.id.car_icon);
        ImageView prof_iconButton = findViewById(R.id.prof_icon);

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
        LatLng curveRoadLatLng = new LatLng(10.796409472641445, 123.99460939973841);
        googleMap.addMarker(new MarkerOptions().position(curveRoadLatLng).title("Curve Road"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curveRoadLatLng, 15f));

        LatLng geofenceCenter = new LatLng(10.796409472641445, 123.99460939973841); // Example coordinates
        float radius = 10; // in meters
        googleMap.addCircle(new CircleOptions()
                .center(geofenceCenter)
                .radius(radius)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(64, 200, 0, 0)));

        // Register geofences
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(mGeofenceList)
                .build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, broadcastreceiver.class);
        intent.setAction("GEOFENCE_TRANSITION");
        intent.putExtra("transitionType", Geofence.GEOFENCE_TRANSITION_ENTER); // Example transition type
        intent.putExtra("geofenceId", "CurveRoad"); // Example geofence ID
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate();
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

    private static class broadcastreceiver {
    }
}
