package com.kosrat.alarm.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Helper class to create alarm in specific time.
 */

public class AlarmManagerUtils extends BroadcastReceiver {

    private static final int PENDING_INTENT_REQUEST_CODE = 1;

    /**
     * Helper method to create pending intent so that it will tell the system where should to
     * go when the alarm triggered.
     *
     * @param context Application context.
     * @return {@link PendingIntent}
     */
    public static PendingIntent createPendingIntent(Context context) {

        Intent intent = new Intent(context, AlarmManager.class);
        return PendingIntent.getBroadcast(context, PENDING_INTENT_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Helper method to create exact alarm which will be fired at the exact time in all
     * api levels even in standby, idle, or doze mode.
     *
     * @param context  Application context
     * @param calendar Calendar instance which is the alarm will trigger at this time.
     */
    public static void createExactAlarm(Context context, Calendar calendar) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = createPendingIntent(context);

        Log.i(TAG, "createExactAlarm: " + calendar.getTime().toString()); //NON-NLS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    /**
     * Helper method to create exact repeating alarm which will be fired at the exact time in all
     * api levels even in standby, idle, or doze mode.
     *
     * @param context  Application context.
     * @param calendar Calendar instance which is the alarm will trigger approximately at this time.
     * @param interval Repeating interval in milliseconds.
     */
    public static void createExactRepeatingAlarm(Context context, Calendar calendar, long interval) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = createPendingIntent(context);

        Log.i(TAG, "createExactRepeatingAlarm: " + calendar.getTime().toString()); //NON-NLS

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, alarmIntent);
    }

    /**
     * Helper method to create inexact alarm which is not necessary to fire at exact time and
     * this method is better for battery optimization.
     *
     * @param context  Application context.
     * @param calendar Calendar instance which is the alarm will trigger approximately at this time.
     */
    public static void createInexactAlarm(Context context, Calendar calendar) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = createPendingIntent(context);

        Log.i(TAG, "createInexactAlarm: " + calendar.getTime().toString()); //NON-NLS

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

    /**
     * Helper method to create inexact repeating alarm which is not necessary to fire at exact time
     * and this method is better for battery optimization.
     *
     * @param context  Application context.
     * @param calendar Calendar instance which is the alarm will trigger approximately at this time.
     * @param interval Repeating interval in milliseconds.
     */
    public static void createInexactRepeatingAlarm(Context context, Calendar calendar, long interval) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = createPendingIntent(context);

        Log.i(TAG, "createInexactRepeatingAlarm: Alarm -> " + calendar.getTime().toString()); //NON-NLS

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
