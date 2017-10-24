package com.coffee.coffeeclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import static com.coffee.coffeeclock.R.id.timePicker;

public class CreateNewAlarm extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_alarm);

        //Update the text to match the SeekBar
        final SeekBar coffeeLevelSB = (SeekBar)findViewById(R.id.coffeeLevelSeekBar);
        final TextView coffeeLevelT = (TextView)findViewById(R.id.coffeeLevelText);
        coffeeLevelSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress <= 33)
                    coffeeLevelT.setText(getString(R.string.coffee_level_l));
                else if (progress >= 66)
                    coffeeLevelT.setText(getString(R.string.coffee_level_h));
                else
                    coffeeLevelT.setText(getString(R.string.coffee_level_m));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void makeAlarm()
    {
        //method to actually submit an alarm, needs to be fixed later

        /*Intent intent = new Intent(this, AlarmManager.class);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        AlarmManager.addAlarm(timePicker.getHour(), timePicker.getMinute());
        startActivity(intent);*/
    }
}
