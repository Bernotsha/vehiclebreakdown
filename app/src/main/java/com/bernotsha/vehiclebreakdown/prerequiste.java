package com.bernotsha.vehiclebreakdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class prerequiste extends AppCompatActivity {
    Button findshop,enableper;
    private FusedLocationProviderClient client;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerequiste);
        findshop=findViewById(R.id.findshop);
        reference= FirebaseDatabase.getInstance().getReference();
        //dialog();
        findshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLocationEnabled()) {
                    request();
                }
                else if (ActivityCompat.checkSelfPermission(prerequiste.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(prerequiste.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    request1();
                }
                else
                {
                    updatelocation();
                    Intent i=new Intent(prerequiste.this,shoplist.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void updatelocation() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(prerequiste.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(prerequiste.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(prerequiste.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                try {


                    if (location != null) {
                        Toast.makeText(prerequiste.this,String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
                        Userlocation location1 = new Userlocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        reference.child("Registration").child("8608391955").child("Current location").setValue(location1);


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    private void dialog() {
        ViewGroup v = findViewById(R.id.content);
        View Dialog = LayoutInflater.from(this).inflate(R.layout.permissionaccept,v,false);
        androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
        b.setView(Dialog);
        final androidx.appcompat.app.AlertDialog alertDialog=b.create();
        enableper=Dialog.findViewById(R.id.enable);
        enableper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request1();
                request();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

    }

    private void request()
    {

        if(!isLocationEnabled())
        {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            final String message = "GPS Connection Required. Turn ON your GPS";
            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        }



    }
    private void request1()
    {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
}
