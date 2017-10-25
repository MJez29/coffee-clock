package com.coffee.coffeeclock;


public class Alarm {

    private int hour;
    private int minute;
    private String size;

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
        return "" + hour + ":" + minute;
    }

}
