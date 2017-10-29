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
    DecimalFormat timeFormat = new DecimalFormat("00");

    //Default constructor
    public Alarm(){
        this(0, 0, "Medium");
    }
    public Alarm(int hour, int minute, String size){
        this.hour = hour;
        this.minute = minute;
        this.size = size;
    }
    @Override
    public String toString(){
        return "" + timeFormat.format(hour) + ":" + timeFormat.format(minute) +
                " || " + size;
    }

    public void alarmOn(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        // The unique ID of the alarm is HHMM as an integer
        PendingIntent pi = PendingIntent.getBroadcast(context, hour * 100 + minute, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
}
