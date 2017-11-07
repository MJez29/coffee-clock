package com.coffee.coffeeclock;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static final int SET_ALARM_REQUEST = 1;
    static final int EDIT_ALARM_REQUEST = 2;
    static final ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    ListView alarmListView;
    AlarmArrayAdapter<Alarm> alarmListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alarmListView = (ListView) findViewById(R.id.alarmListView);
        alarmListAdapter = new AlarmArrayAdapter<Alarm>(this,
                android.R.layout.simple_list_item_1, alarms);
        alarmListView.setAdapter(alarmListAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        /*Intent notifIntent = getIntent();
        if(notifIntent != null)
        {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notifIntent.getIntExtra("id", 0));
        }*/
        alarmListAdapter.notifyDataSetChanged();
    }

    //method to start the "create new alarm" activity
    public void createNewAlarm(View view) {
        Intent intent = new Intent(this, CreateNewAlarm.class);
        startActivityForResult(intent, SET_ALARM_REQUEST);
    }

    public void testMethod(View view) {
        Toast.makeText(this, IdGenerator.getid()+"" , Toast.LENGTH_SHORT).show();
        AlarmReceiver ar = new AlarmReceiver();
        ar.onReceive(this, new Intent(this, this.getClass()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_ALARM_REQUEST) {
            // Add an alarm to the list if one was created
            if (resultCode == RESULT_OK) {
                alarms.add(new Alarm(data.getIntExtra("alarm_hour", 0),
                                     data.getIntExtra("alarm_minute", 0),
                                     data.getStringExtra("alarm_size"), this));
                alarms.get(alarms.size() - 1).alarmOn(this);
            }
        }
        if (requestCode == EDIT_ALARM_REQUEST) {
            // Change the alarm that was edited
            if (resultCode == RESULT_OK) {
                alarms.get(data.getIntExtra("index", 0)).alarmOff(this);
                alarms.remove(data.getIntExtra("index", 0));
                alarms.add(new Alarm(data.getIntExtra("alarm_hour", 0),
                        data.getIntExtra("alarm_minute", 0),
                        data.getStringExtra("alarm_size"), this));
                alarms.get(alarms.size() - 1).alarmOn(this);
            }
        }
        alarmListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
