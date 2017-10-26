package com.coffee.coffeeclock;

import android.content.Intent;
import android.os.Bundle;
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
    static final ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    ListView alarmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alarmListView = (ListView) findViewById(R.id.alarmListView);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //in the future, will update with the newest alarms every time MainActivity returns to focus
        AlarmArrayAdapter<Alarm> alarmListAdapter = new AlarmArrayAdapter<Alarm>(this,
                android.R.layout.simple_list_item_1, alarms);
        alarmListView.setAdapter(alarmListAdapter);
    }

    //method to start the "create new alarm" activity
    public void createNewAlarm(View view) {
        Intent intent = new Intent(this, CreateNewAlarm.class);
        startActivityForResult(intent, SET_ALARM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_ALARM_REQUEST) {
            // Add an alarm to the list if one was created
            if (resultCode == RESULT_OK) {
                alarms.add(new Alarm(data.getIntExtra("alarm_hour", 0),
                                     data.getIntExtra("alarm_minute", 0),
                                     data.getStringExtra("alarm_size")));
            }
        }
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
