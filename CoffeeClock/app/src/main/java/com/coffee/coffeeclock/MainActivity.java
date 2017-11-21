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

/*
    MainActivity
    Contains the ListView containing all alarms, and
    the FAB used to create new alarms.
    Called upon starting the application.
 */

public class MainActivity extends AppCompatActivity
{
    public static final int SET_ALARM_REQUEST = 1;
    public static final int EDIT_ALARM_REQUEST = 2;

    private static ArrayList<MyAlarm> myAlarms = new ArrayList<MyAlarm>();
    private static final String alarmStorageFile = "alarmStorage.txt"; // File in Internal Storage
    private ListView alarmListView;
    private AlarmArrayAdapter<MyAlarm> alarmListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recover previous alarms and ID on application start
        readAlarmStorage();
        IdGenerator.setid(findLargestId());

        alarmListView = (ListView) findViewById(R.id.alarmListView);
        alarmListAdapter = new AlarmArrayAdapter<MyAlarm>(this,
                android.R.layout.simple_list_item_1, myAlarms);
        alarmListView.setAdapter(alarmListAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Sort alarms when returning from an alarm create/edit request
        Collections.sort(myAlarms);
        alarmListAdapter.notifyDataSetChanged();
    }

    // Returns the largest ID of all active alarms + 1 for the IdGenerator
    private int findLargestId()
    {
        int largest = -1;
        for(int i = 0; i < myAlarms.size(); i++)
        {
            if(myAlarms.get(i).id > largest)
                largest = myAlarms.get(i).id;
        }
        return largest + 1;
    }

    // Call to start CreateNewAlarm Activity
    public void createNewAlarm(View view)
    {
        Intent newAlarmIntent = new Intent(this, CreateNewAlarm.class);
        startActivityForResult(newAlarmIntent, SET_ALARM_REQUEST);
    }

    // Called when an alarm is done being created or edited
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Add an alarm to the list if one was created
        if (requestCode == SET_ALARM_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                myAlarms.add(new MyAlarm(data.getIntExtra("alarm_hour", 0),
                                     data.getIntExtra("alarm_minute", 0),
                                     data.getStringExtra("alarm_size"), this));

                // Activate the newly added alarm
                myAlarms.get(myAlarms.size() - 1).alarmOn(this);
            }
        }

        // Modify an alarm in the list if one was edited
        if (requestCode == EDIT_ALARM_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                myAlarms.get(data.getIntExtra("index", 0)).alarmOff(this);
                myAlarms.remove(data.getIntExtra("index", 0));
                myAlarms.add(new MyAlarm(data.getIntExtra("alarm_hour", 0),
                        data.getIntExtra("alarm_minute", 0),
                        data.getStringExtra("alarm_size"), this));
                // Activate the newly added alarm
                myAlarms.get(myAlarms.size() - 1).alarmOn(this);
            }
        }

        // Put the alarms in chronological order
        Collections.sort(myAlarms);

        updateAlarmStorage();
        alarmListAdapter.notifyDataSetChanged();
    }

    // Write all current alarms to the internal storage
    public void updateAlarmStorage()
    {
        // Remove current internal storage file
        deleteFile(alarmStorageFile);

        try
        {
            FileOutputStream fos = openFileOutput(alarmStorageFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myAlarms);
            oos.close();
        }
        catch (Exception e)
        {
            // Display the error in a Toast
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Read all existing alarms from the internal storage
    private void readAlarmStorage()
    {
        try
        {
            FileInputStream fis = openFileInput(alarmStorageFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            myAlarms.clear();
            myAlarms = (ArrayList<MyAlarm>) ois.readObject();
            for(MyAlarm al : myAlarms)
                al.instantiateAM(this); // Instantiate the only transient field
        }
        catch (FileNotFoundException e)
        {
            // We throw away this exception since it occurs when the app is first opened
        }
        catch (Exception e)
        {
            // Display the error in a Toast
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Method for removing a specific alarm
    public void removeAlarm(final int index)
    {
        // Display confirmation dialog for alarm deletion
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        adBuilder.setMessage("Are you sure you want to delete this alarm?")
                .setTitle("Coffee Clock")
                .setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            // Delete alarm and perform all required cleanup
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

    // Default code below is from the creation of this project in Android Studio

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
