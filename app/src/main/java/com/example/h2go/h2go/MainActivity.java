package com.example.h2go.h2go;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String urlYelp = "https://api.yelp.com/v3/businesses/search?sort_by=rating&";
    String authUrl = "https://api.yelp.com/oauth2/token";
    String token = "";

    String urlWeather = "http://api.openweathermap.org/data/2.5/weather?";

    public static String[] filtered;

    private static BroadcastReceiver notice;

    private static int broadcastType = -1;
    public static boolean filter_done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        StringRequest tokenReq = new StringRequest(Request.Method.POST, authUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    token = (String)res.get("access_token");
                }
                catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("grant_type", "client_credentials");
                data.put("client_id", "QHXJ7sOQHbvISqzf1zRf3Q");
                data.put("client_secret", "fnSA3VxBJzerT89dviQCKUQjLm6FhhUe5aAFzcYTHsXTcmT47s81mUk34HHdQt7H");
                return data;
            }
        };

        queue.add(tokenReq);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUrl = urlYelp + "categories=beaches&location=San+Diego";
                JsonObjectRequest getRequest = yelpReq(newUrl);
                queue.add(getRequest);
                queue.add(weatherReq(urlWeather + "q=London" + "&APPID=e645777286ee1242f55c2205a776402a"));
            }
        });
        notice = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if(action.equals("filter_done")) {

                }
            }
        };
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

    private JsonObjectRequest yelpReq(String queryUrl) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, queryUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int tot = (int)response.get("total");
                    if (tot > 19) tot = 19;
                    for (int i = 0; i < tot; i++) {
                        System.out.println("Name: "+((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("id"));
                        System.out.println("Rating: "+((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("rating"));
                        if (((JSONObject) ((JSONArray) response.get("businesses")).get(i)).has("price"))
                            System.out.println("Price: "+((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("price"));
                        else
                            System.out.println("Price: Not Applicable");
                        System.out.println("Closed: "+((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("is_closed"));
                        System.out.println("Latitude: "+((JSONObject)((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("coordinates")).get("latitude"));
                        System.out.println("Longitude: "+((JSONObject)((JSONObject) ((JSONArray) response.get("businesses")).get(i)).get("coordinates")).get("longitude"));
                    }
                }
                catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        return getRequest;
    }

    private JsonObjectRequest weatherReq(String queryUrl) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, queryUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(((JSONObject)((JSONArray)response.get("weather")).get(0)).get("main"));
                }
                catch (JSONException e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        return getRequest;
    }
}
