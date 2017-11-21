package com.coffee.coffeeclock;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

/*
    CreateNewAlarm
    An Activity containing UI to set a new alarm
 */

public class CreateNewAlarm extends AppCompatActivity
{
    private String coffeeSize;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_alarm);

        final SeekBar coffeeLevelSB = (SeekBar)findViewById(R.id.coffeeLevelSeekBar);
        final TextView coffeeLevelText = (TextView)findViewById(R.id.coffeeLevelText);
        coffeeSize = getString(R.string.coffee_level_l); // Default value

        // Update text to match SeekBar
        coffeeLevelSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(progress <= 33) {
                    coffeeLevelText.setText(getString(R.string.coffee_level_indicator)
                            .concat(getString(R.string.coffee_level_l)));
                    coffeeSize = getString(R.string.coffee_level_l);
                }
                else if (progress >= 66){
                    coffeeLevelText.setText(getString(R.string.coffee_level_indicator)
                            .concat(getString(R.string.coffee_level_h)));
                    coffeeSize = getString(R.string.coffee_level_h);
                }
                else{
                    coffeeLevelText.setText(getString(R.string.coffee_level_indicator)
                            .concat(getString(R.string.coffee_level_m)));
                    coffeeSize = getString(R.string.coffee_level_m);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void makeAlarm(View view)
    {
        Intent returnIntent = new Intent(this, MainActivity.class);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);

        // SDK-dependent TimePicker access
        if(Build.VERSION.SDK_INT < 23)
        {
            returnIntent.putExtra("alarm_hour", timePicker.getCurrentHour());
            returnIntent.putExtra("alarm_minute", timePicker.getCurrentMinute());
        }
        else
        {
            returnIntent.putExtra("alarm_hour", timePicker.getHour());
            returnIntent.putExtra("alarm_minute", timePicker.getMinute());
        }
        returnIntent.putExtra("alarm_size", coffeeSize);

        // Add the index this Activity was called with to the returned intent
        Intent startingIntent = getIntent();
        returnIntent.putExtra("index", startingIntent.getIntExtra("index", 0));

        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
