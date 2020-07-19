package com.bernotsha.vehiclebreakdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int time_out=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
                final boolean first= prefs.getBoolean("firststart",false);

                if(first)
                {
                    Intent i = new Intent(MainActivity.this,prerequiste.class);
                    startActivity(i);
                    finish();

                }
                else{
                    Intent i = new Intent(MainActivity.this,termsandconditions.class);
                    startActivity(i);
                    finish();
                }

            }
        },time_out);
    }
}