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
import android.widget.Toast;

import java.util.List;

public class AlarmArrayAdapter<Alarm> extends ArrayAdapter<Alarm> {

    public AlarmArrayAdapter(Context context, int resource, List<Alarm> objects)
    {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        View itemView = null;
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(this.getContext());
            itemView = li.inflate(R.layout.alarm_list_row, null);
        } else {
            itemView = convertView;
        }
        final TextView alarmDesc = (TextView)itemView.findViewById(R.id.alarmDesc);
        final MyAlarm currMyAlarm =
                (MyAlarm)getItem(position);
        alarmDesc.setText(currMyAlarm.toString());

        Switch alarmSwitch = (Switch)itemView.findViewById(R.id.alarmOnOff);
        // Turn alarm on and off
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    currMyAlarm.alarmOn(parent.getContext());
                }
                else {
                    currMyAlarm.alarmOff(parent.getContext());
                }
            }
        });

        Button editButton = (Button)itemView.findViewById(R.id.editAlarmBtn);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), CreateNewAlarm.class);
                intent.putExtra("index", position);
                ((Activity)parent.getContext()).startActivityForResult(intent,
                        MainActivity.EDIT_ALARM_REQUEST);
            }
        });

        Button deleteButton = (Button)itemView.findViewById(R.id.deleteAlarmBtn);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.myAlarms.remove(position);
                ((MainActivity)parent.getContext()).updateAlarmStorage();
                // Call to MainActivity within static context
                notifyDataSetChanged();
            }
        });

        // POST Request template
        /*editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                                                RequestManager.requestURL, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with the response
                            try
                            {
                                alarmDesc.setText(
                                        RequestManager.checkStatus(response.getInt("status")));
                            }
                            catch(org.json.JSONException e){
                                // Whoops
                                alarmDesc.setText(e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error
                        }
                    }
                ){
                    @Override
                    public byte[] getBody()
                    {
                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try
                        {
                            jsonObject.put("coffeeSize", "Medium");
                            body = jsonObject.toString();
                        } catch (JSONException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        try
                        {
                            return body.getBytes("utf-8");
                        } catch (UnsupportedEncodingException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                RequestManager.getInstance(getContext()).addToRequestQueue(postRequest);
            }
        });*/
        return itemView;
    }
}
