package com.coffee.coffeeclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import static android.app.Notification.PRIORITY_HIGH;
import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.support.v4.app.NotificationManagerCompat.IMPORTANCE_HIGH;

/**
 * Created by James on 2017-10-29.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        /*Intent onSnoozeIntent = new Intent(context, MainActivity.class);
        onSnoozeIntent.putExtra("id", id);

        PendingIntent pi = PendingIntent.getActivity(context, 1, onSnoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        .addAction(R.drawable.ic_stat_name,
                                "Open App", pi) */

        Intent onClickIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 1, onClickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri notification =
                Uri.parse("android.resource://"+context.getPackageName()+"/raw/alarm_tone");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setTicker("Coffee Clock")
                        .setSmallIcon(R.mipmap.ic_stat_onesignal_default)
                        .setContentTitle("Notification title")
                        .setContentText("Your coffee is brewing, coffee size: "
                                + intent.getStringExtra("alarm_size"))
                        .setVisibility(VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                        .setContentInfo("Info")
                        .setContentIntent(pi)
                        .setSound(notification);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(IdGenerator.getid(), mBuilder.build());
    }
}

