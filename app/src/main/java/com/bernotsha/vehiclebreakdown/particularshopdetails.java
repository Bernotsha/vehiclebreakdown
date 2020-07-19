package com.bernotsha.vehiclebreakdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;

public class particularshopdetails extends AppCompatActivity {
ImageView pimage;
Button bookingconfirm;
Intent intent;
TextView pshopname,pname,pmobile,pstreet,parea,ptown,pstate,pdistrict,ppincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particularshopdetails);
        intent=getIntent();
        bookingconfirm=findViewById(R.id.bookingconfirm);
        pimage=findViewById(R.id.pimage);
        pshopname=findViewById(R.id.pshopname);
        pname=findViewById(R.id.pname);
        pmobile=findViewById(R.id.pmobile);
        pstreet=findViewById(R.id.pstreet);
        parea=findViewById(R.id.parea);
        ptown=findViewById(R.id.ptown);
        pstate=findViewById(R.id.pstate);
        pdistrict=findViewById(R.id.pdistrict);
        ppincode=findViewById(R.id.ppincode);
        String image=intent.getStringExtra("shopimage1");
        Picasso.get().load(image).into(pimage);
        shopdescription();
        bookingconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Bookingconfirmed.class));
            }
        });


    }





    private void shopdescription() {

        String shopname=intent.getStringExtra("shopname1");
        pshopname.setText(shopname);
        String owner=intent.getStringExtra("name1");
        pname.setText(owner);
        String mobile=intent.getStringExtra("mobile1");
        pmobile.setText(mobile);
        String street=intent.getStringExtra("street1");
        pstreet.setText(street);
        String area=intent.getStringExtra("area1");
        parea.setText(area);
        String town=intent.getStringExtra("town1");
        ptown.setText(town);
        String district=intent.getStringExtra("district1");
        pdistrict.setText(district);
        String state=intent.getStringExtra("state1");
        pstate.setText(state);
        String pincode=intent.getStringExtra("pincode1");
        ppincode.setText(pincode);

    }
}
