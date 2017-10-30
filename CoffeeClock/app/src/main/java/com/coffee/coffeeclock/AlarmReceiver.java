package com.coffee.coffeeclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by James on 2017-10-29.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
    }
}

