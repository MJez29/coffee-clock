package com.coffee.coffeeclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    static final int SET_ALARM_REQUEST = 1;
    static final int EDIT_ALARM_REQUEST = 2;
    static ArrayList<MyAlarm> myAlarms = new ArrayList<MyAlarm>();
    static final String alarmStorageFile = "alarmStorage.txt";
    ListView alarmListView;
    AlarmArrayAdapter<MyAlarm> alarmListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        readAlarmStorage();
        IdGenerator.setid(findLargestId());
        alarmListView = (ListView) findViewById(R.id.alarmListView);
        alarmListAdapter = new AlarmArrayAdapter<MyAlarm>(this,
                android.R.layout.simple_list_item_1, myAlarms);
        alarmListView.setAdapter(alarmListAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Collections.sort(myAlarms);
        alarmListAdapter.notifyDataSetChanged();
    }

    private int findLargestId(){
        int largest = -1;
        for(int i = 0; i < myAlarms.size(); i++)
        {
            if(myAlarms.get(i).id > largest)
                largest = myAlarms.get(i).id;
        }
        return largest + 1;
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
                myAlarms.add(new MyAlarm(data.getIntExtra("alarm_hour", 0),
                                     data.getIntExtra("alarm_minute", 0),
                                     data.getStringExtra("alarm_size"), this));
                myAlarms.get(myAlarms.size() - 1).alarmOn(this);
            }
        }
        if (requestCode == EDIT_ALARM_REQUEST) {
            // Change the alarm that was edited
            if (resultCode == RESULT_OK) {
                myAlarms.get(data.getIntExtra("index", 0)).alarmOff(this);
                myAlarms.remove(data.getIntExtra("index", 0));
                myAlarms.add(new MyAlarm(data.getIntExtra("alarm_hour", 0),
                        data.getIntExtra("alarm_minute", 0),
                        data.getStringExtra("alarm_size"), this));
                myAlarms.get(myAlarms.size() - 1).alarmOn(this);
            }
        }
        Collections.sort(myAlarms);
        updateAlarmStorage();
        alarmListAdapter.notifyDataSetChanged();
    }


    public void updateAlarmStorage(){
        //Toast.makeText(this, "UpdateAlarmStorage called", Toast.LENGTH_SHORT).show();
        deleteFile(alarmStorageFile);
        try {
            FileOutputStream fos = openFileOutput(alarmStorageFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myAlarms);
            oos.close();
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readAlarmStorage(){

        //Toast.makeText(this, "ReadAlarmStorage called", Toast.LENGTH_SHORT).show();
        try {
            FileInputStream fis = openFileInput(alarmStorageFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            myAlarms.clear();
            myAlarms = (ArrayList<MyAlarm>) ois.readObject();
            for(MyAlarm al : myAlarms)
                al.instantiateAM(this); // Transient field
        }
        catch (FileNotFoundException e) {
            // We throw away this exception since it occurs when the app is first opened
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeAlarm(final int index){
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setMessage("Are you sure you want to delete this alarm?")
                .setTitle("Coffee Clock")
                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                myAlarms.remove(index);
                                updateAlarmStorage();
                                alarmListAdapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = adBuilder.create();
        dialog.show();
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
