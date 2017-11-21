package com.coffee.coffeeclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

/*
    MyAlarm
    Serializable class representing an alarm object
    Contains all of the information about any given alarm
 */

public class MyAlarm implements Comparable<MyAlarm>, Serializable
{
    private int hour;
    private int minute;
    private String size;
    public boolean switchState; // Whether the alarm is on
    public int id;

    private Calendar calendar;
    transient private AlarmManager alarmManager;
    private DecimalFormat timeFormat = new DecimalFormat("00");

    // Default constructor, creates a 00:00 alarm for Small coffee
    public MyAlarm(Context context)
    {
        this(0, 0, "Small", context);
    }

    public MyAlarm(int hour, int minute, String size, Context context)
    {
        this.hour = hour;
        this.minute = minute;
        this.size = size;
        this.switchState = true;
        this.id = IdGenerator.getid();

        // Create Calendar object corresponding to the set alarm time
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public String toString()
    {
        // String format:
        // "Hour:Minute || Size"
        return "" + timeFormat.format(hour) + ":" + timeFormat.format(minute) +
                " || " + size;
    }

    // Instantiate transient field
    public void instantiateAM(Context context)
    {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    // Method called when alarm is "turned on," either via switch or creation
    public void alarmOn(Context context)
    {
        Calendar currentTime = Calendar.getInstance();

        // Only set the alarm if the time hasn't already passed
        if(calendar.getTimeInMillis() > currentTime.getTimeInMillis()) {
            Intent setAlarmIntent = new Intent(context, AlarmReceiver.class);
            setAlarmIntent.putExtra("alarm_hour", hour);
            setAlarmIntent.putExtra("alarm_minute", minute);
            setAlarmIntent.putExtra("alarm_size", size);
            PendingIntent pi = PendingIntent.getBroadcast(context, id, setAlarmIntent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }

    // Method called with alarm is "turned off," either via switch or destruction
    public void alarmOff(Context context)
    {
        Intent cancelAlarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, cancelAlarmIntent, 0);
        alarmManager.cancel(pi);
    }

    // Compares alarms such that they are in chronological order
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
