package com.coffee.coffeeclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlarmArrayAdapter<Alarm> extends ArrayAdapter<Alarm> {
    public AlarmArrayAdapter(Context context, int resource, List<Alarm> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View itemView = null;
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(this.getContext());
            itemView = li.inflate(R.layout.alarm_list_row, null);
        } else {
            itemView = convertView;
        }
        TextView alarmDesc = (TextView)itemView.findViewById(R.id.alarmDesc);
        Alarm currAlarm = getItem(position);
        alarmDesc.setText(currAlarm.toString());
        return itemView;
    }
}
