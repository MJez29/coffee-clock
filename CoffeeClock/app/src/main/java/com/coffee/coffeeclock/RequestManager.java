package com.coffee.coffeeclock;

import android.content.Context;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    private static RequestManager requestManager;
    public static String requestURL = "http://date.jsontest.com";
    private  RequestQueue requestQueue;
    private  Context context;

    private RequestManager(Context context)
    {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized RequestManager getInstance(Context context)
    {
        if(requestManager == null){
            requestManager = new RequestManager(context);
        }
        return requestManager;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }

    // God knows when these methods will be used, more like syntax references
    // Create a GET request
    public void sendGetRequest() {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, requestURL, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    // Do something with the response
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
        requestQueue.add(getRequest);
    }

    // Create a POST request
    public void sendPostRequest() {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the object
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("coffeeSize", "Medium"); // Temporary value to be replaced
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    // Create a DELETE request
    public void sendDeleteRequest() {
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the object
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
        requestQueue.add(deleteRequest);
    }
}
