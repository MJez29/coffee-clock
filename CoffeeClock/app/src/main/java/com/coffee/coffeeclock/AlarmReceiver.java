package com.coffee.coffeeclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static android.app.Notification.VISIBILITY_PUBLIC;

/*
    AlarmReceiver
    A custom broadcast receiver called when an alarm is triggered
 */

public class AlarmReceiver extends BroadcastReceiver
{
    private String serverResponseStatus; // Coffee status from the server

    private final String requestURL = "http://192.168.137.47:3000/brew";
    private final String[] orderStatus = {"OK", "INVALID_ORDER_NUMBER", "CANNOT_DELETE_ORDER",
            "INTERNAL_BREWING_ERROR", "INVALID_BREW_SIZE"};

    public void onReceive(final Context context, final Intent intent)
    {
        serverResponseStatus = context.getString(R.string.server_response_default);

        // Send out a POST request with coffee size
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                requestURL,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            // Translate returnedStatus from int to String
                            int statusInt = response.getInt("status");
                            serverResponseStatus = orderStatus[statusInt];

                            // Only send notification AFTER response from the server
                            sendAlarmNotif(context, intent);
                        }
                        catch(Exception e)
                        {
                            // Display the error in a Toast
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError e)
                    {
                        if (e.networkResponse == null)
                        {
                            if (e.getClass().equals(TimeoutError.class))
                            {
                                // Set off alarm with error if there is no server response
                                sendAlarmNotif(context, intent);
                            }
                        }
                        else
                        {
                            // Display the error in a Toast
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        {
            @Override
            public byte[] getBody()
            {
                JSONObject coffeeJSONRequest = new JSONObject();
                String body = null;
                try
                {
                    coffeeJSONRequest.put("coffeeSize", intent.getStringExtra("alarm_size"));
                    body = coffeeJSONRequest.toString();
                }
                catch (JSONException e)
                {
                    // Display the error in a Toast
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }

                try
                {
                    return body.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    // Display the error in a Toast
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };

        requestQueue.add(postRequest);
    }

    private void sendAlarmNotif(final Context context, final Intent intent)
    {
        // Send user to application upon notification press
        Intent onClickIntent = new Intent(context, MainActivity.class);
        PendingIntent goToApp = PendingIntent.getActivity(context, 1, onClickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Custom alarm tone
        Uri alarmSound =
                Uri.parse("android.resource://"+context.getPackageName()+"/raw/alarm_tone");

        // Construct and fire off a notification
        NotificationCompat.Builder alarmNotifBuilder = new NotificationCompat.Builder(context)
                        .setTicker("Coffee Clock")
                        .setContentTitle("Coffee Alarm Triggered!")
                        .setContentText("Coffee brewing! Size: "
                            + intent.getStringExtra("alarm_size")
                            + ", Status: "
                            + serverResponseStatus)
                        .setSmallIcon(R.mipmap.ic_stat_onesignal_default)
                        .setVisibility(VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                        .setContentIntent(goToApp)
                        .setSound(alarmSound);

        NotificationManager notifManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Generate an ID for the notification in order to avoid conflicts
        notifManager.notify(IdGenerator.getid(), alarmNotifBuilder.build());
    }
}

