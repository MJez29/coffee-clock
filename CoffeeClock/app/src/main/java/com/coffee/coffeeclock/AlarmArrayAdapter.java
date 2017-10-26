package com.coffee.coffeeclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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
        final TextView alarmDesc = (TextView)itemView.findViewById(R.id.alarmDesc);
        Alarm currAlarm = getItem(position);
        alarmDesc.setText(currAlarm.toString());

        Button editButton = (Button)itemView.findViewById(R.id.editAlarmBtn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,
                                                RequestManager.requestURL, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with the response
                            try
                            {
                                alarmDesc.setText(response.getString("time"));
                            }
                            catch(org.json.JSONException e){
                                // Whoops
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
                );
                RequestManager.getInstance(getContext()).addToRequestQueue(getRequest);
            }
        });
        return itemView;
    }
}
