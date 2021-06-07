package com.app.trackit.model.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.trackit.R;
import com.app.trackit.ui.MainActivity;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    public void showNotification(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, MainActivity.notificationChannelId)
                .setSmallIcon(R.drawable.ic_arm)
                .setContentTitle("TrackIt")
                .setContentText("Ricorda di fare una foto!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(666, mBuilder.build());
    }
}