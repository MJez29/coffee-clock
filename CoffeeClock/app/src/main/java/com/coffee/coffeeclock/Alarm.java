package com.coffee.coffeeclock;


import java.text.DecimalFormat;

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

}
