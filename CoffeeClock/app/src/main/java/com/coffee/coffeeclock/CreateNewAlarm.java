package com.coffee.coffeeclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

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

    public void makeAlarm(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        intent.putExtra("alarm_hour", timePicker.getHour());
        intent.putExtra("alarm_minute", timePicker.getMinute());
        setResult(RESULT_OK, intent);
        finish();
    }
}
