package com.coffee.coffeeclock;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MyAlarm implements Comparable<MyAlarm>, Serializable{

    private int hour;
    private int minute;
    private String size;
    public boolean switchState;
    public int id;

    Calendar calendar;
    transient private AlarmManager alarmManager;
    DecimalFormat timeFormat = new DecimalFormat("00");

    public MyAlarm(Context context)
    {
        this(0, 0, "Small", context);
    }

    public MyAlarm(int hour, int minute, String size, Context context){
        this.hour = hour;
        this.minute = minute;
        this.size = size;
        this.switchState = true;
        this.id = IdGenerator.getid();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    @Override
    public String toString(){
        return "" + timeFormat.format(hour) + ":" + timeFormat.format(minute) +
                " || " + size;
    }

    public void instantiateAM(Context context){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void alarmOn(Context context) {
        Calendar currentTime = Calendar.getInstance();
        // Only set the timer if the time hasn't already passed
        if(calendar.getTimeInMillis() > currentTime.getTimeInMillis()) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("alarm_hour", hour);
            intent.putExtra("alarm_minute", minute);
            intent.putExtra("alarm_size", size);
            PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }

    public void alarmOff(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmManager.cancel(pi);
    }

    public int compareTo(MyAlarm al)
    {
        if(this.hour > al.hour)
            return 1;
        if(this.hour < al.hour)
            return -1;
        else
        {
            if(this.minute > al.minute)
                return 1;
            if(this.minute < al.minute)
                return -1;
            else
            {
                return 0;
            }
        }
    }
}
