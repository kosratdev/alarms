package com.kosrat.alarm.managers;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.kosrat.alarm.MainActivity;
import com.kosrat.alarm.R;

/**
 * A service class to handle alarms when triggered.
 */

public class AlarmService extends IntentService {

    private static final int NOTIFICATION_ID = 100;
    private static final int PENDING_INTENT_ID = 200;

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public AlarmService() {
        super("Alarm Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sendNotification();
    }

    private void sendNotification() {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_white)
                .setLargeIcon(largeIcon)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent())
                .setAutoCancel(true);

        // to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * This method will create the pending intent which will trigger when the notification
     * is pressed. This pending intent should open up the MainActivity.
     *
     * @return PendingIntent
     */
    private PendingIntent contentIntent() {

        Intent startActivityIntent = new Intent(this, MainActivity.class);

        return PendingIntent.getActivity(this,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
