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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coffee.coffeeclock.MainActivity.SET_ALARM_REQUEST;

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
        final com.coffee.coffeeclock.Alarm currAlarm =
                (com.coffee.coffeeclock.Alarm)getItem(position);
        alarmDesc.setText(currAlarm.toString());

        Switch alarmSwitch = (Switch)itemView.findViewById(R.id.alarmOnOff);
        // Turn alarm on and off
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    currAlarm.alarmOn(parent.getContext());
                }
                else {
                    currAlarm.alarmOff(parent.getContext());
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
