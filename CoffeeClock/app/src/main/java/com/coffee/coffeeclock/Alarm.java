package com.coffee.coffeeclock;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by James on 2017-10-24.
 */

public class Alarm {

    private int hour;
    private int minute;

    //Default constructor
    public Alarm(){
        this(0, 0);
    }
    public Alarm(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }
    @Override
    public String toString(){
        return "" + hour + ":" + minute;
    }
}
