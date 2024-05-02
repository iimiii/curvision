package com.example.intro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;

public class broadcastreceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "GeofenceChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check the transition type
        int transitionType = intent.getIntExtra("transitionType", -1);
        String geofenceId = intent.getStringExtra("geofenceId");

        // Check if the geofence transition was an enter or exit event
        String transitionTypeString = transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ? "Entering" : "Exiting";

        // Create a notification
        String notificationText = transitionTypeString + " " + geofenceId;
        createNotification(context, notificationText);
    }

    private void createNotification(Context context, String text) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Geofence Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Geofence Notification")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(0, builder.build());
    }
}