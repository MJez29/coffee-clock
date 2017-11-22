package com.coffee.coffeeclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/*
    AlarmArrayAdapter
    A custom array adapter used to display custom MyAlarm objects in a ListView
 */

public class AlarmArrayAdapter<E> extends ArrayAdapter<E>
{

    public AlarmArrayAdapter(Context context, int resource, List<E> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        // MyAlarm object being processed
        final MyAlarm currMyAlarm = (MyAlarm)getItem(position);

        View itemView = null;
        if (convertView == null)
        {
            // Inflate view from XML if not already inflated
            LayoutInflater li = LayoutInflater.from(this.getContext());
            itemView = li.inflate(R.layout.alarm_list_row, null);
        }
        else
        {
            itemView = convertView;
        }

        Switch alarmSwitch = (Switch)itemView.findViewById(R.id.alarmOnOff);
        // Turn alarm on and off based on switch movement
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    currMyAlarm.alarmOn(parent.getContext());
                    currMyAlarm.switchState = true;
                }
                else
                {
                    currMyAlarm.alarmOff(parent.getContext());
                    currMyAlarm.switchState = false;
                }
                notifyDataSetChanged();
                // Update internal storage
                ((MainActivity)parent.getContext()).updateAlarmStorage();
            }
        });

        // Update View based on MyAlarm data
        final TextView alarmDesc = (TextView)itemView.findViewById(R.id.alarmDesc);
        alarmDesc.setText(currMyAlarm.toString());
        alarmSwitch.setChecked(currMyAlarm.switchState);

        // EDIT button
        Button editButton = (Button)itemView.findViewById(R.id.editAlarmBtn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open new alarm window with intent to edit
                Intent intent = new Intent(parent.getContext(), CreateNewAlarm.class);
                intent.putExtra("index", position);
                ((Activity)parent.getContext()).startActivityForResult(intent,
                        MainActivity.EDIT_ALARM_REQUEST);
            }
        });

        // DELETE button
        Button deleteButton = (Button)itemView.findViewById(R.id.deleteAlarmBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete alarm from the array of alarms
                ((MainActivity)parent.getContext()).removeAlarm(position);
            }
        });

        return itemView;
    }
}
