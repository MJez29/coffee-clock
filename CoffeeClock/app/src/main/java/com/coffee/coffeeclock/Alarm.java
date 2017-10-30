package com.coffee.coffeeclock;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Alarm {

    private int hour;
    private int minute;
    private String size;
    private Calendar calendar;
    DecimalFormat timeFormat = new DecimalFormat("00");

    //Default constructor
    public Alarm(){
        this(0, 0, "Medium");
    }
    public Alarm(int hour, int minute, String size){
        this.hour = hour;
        this.minute = minute;
        this.size = size;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    @Override
    public String toString(){
        return "" + timeFormat.format(hour) + ":" + timeFormat.format(minute) +
                " || " + size;
    }

    public void alarmOn(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        // The unique ID of the alarm is HHMM as an integer
        PendingIntent pi = PendingIntent.getBroadcast(context, hour * 100 + minute, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public void alarmOff(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        // The unique ID of the alarm is HHMM as an integer
        PendingIntent pi = PendingIntent.getBroadcast(context, hour * 100 + minute, intent, 0);
        alarmManager.cancel(pi);
    }
}
