package com.shubham.umerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.time.TimeTCPClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String rawDate;
    String s_year;
    String s_month;
    String s_day;
    String s_hour;
    String s_min;
    Button b;

    private TimeTCPClient timeTCPClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        timeTCPClient = new TimeTCPClient();

        b = findViewById(R.id.button1);

        try{
            timeTCPClient.connect("time.nist.gov");
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Unable to get date from URL (main activity)", Toast.LENGTH_LONG).show();
        }

        try{
            rawDate = timeTCPClient.getDate().toString();
        }catch(IOException e){
            Toast.makeText(getApplicationContext(), "Unable to get Date (rawDate): " + e.toString(), Toast.LENGTH_LONG).show();
        }

            s_year = rawDate.substring(30, 34);
            s_month = rawDate.substring(4,7);
            s_day = rawDate.substring(8,10);
            s_hour = rawDate.substring(11,13);
            s_min = rawDate.substring(14,16);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                intent.putExtra("Year", s_year);
                intent.putExtra("Month", s_month);
                intent.putExtra("Day", s_day);
                intent.putExtra("Hour", s_hour);
                intent.putExtra("Min", s_min);
                startActivity(intent);
            }
        });

    }



}