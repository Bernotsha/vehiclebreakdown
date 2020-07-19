package com.bernotsha.vehiclebreakdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.heatmaps.WeightedLatLng;

public class shoplist extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private DatabaseReference mRef;
    FirebaseDatabase mfireDatabase;
    DatabaseReference reference;
    String mylati,mylongi;
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);
        reference=FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mylati=dataSnapshot.child("Registration").child("8608391955").child("Current location").child("latitude").getValue().toString();
                mylongi=dataSnapshot.child("Registration").child("8608391955").child("Current location").child("longitude").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef= FirebaseDatabase.getInstance().getReference().child("shop details");
        mRef.keepSynced(true);
        mRecyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mfireDatabase = FirebaseDatabase.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, ViewHolder>(
                Blog.class,
                R.layout.blow_row,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final Blog blog, final int i) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(shoplist.this,particularshopdetails.class);
                    intent.putExtra("shopimage1",blog.getShopimage());
                    intent.putExtra("street1",blog.getStreet());
                    intent.putExtra("area1",blog.getArea());
                    intent.putExtra("town1",blog.getTown());
                    intent.putExtra("district1",blog.getDistrict());
                    intent.putExtra("state1",blog.getState());
                    intent.putExtra("pincode1",blog.getPincode());
                    intent.putExtra("age1",blog.getAge());
                    intent.putExtra("mobile1",blog.getMobile());
                    intent.putExtra("name1",blog.getName());
                    intent.putExtra("shop1",blog.getShop());
                    intent.putExtra("shopname1",blog.getShopname());
                    startActivity(intent);
                    finish();

                }
            });
                String lati=blog.getLatitude();
                String longi=blog.getLongitude();
                LatLng latLng=new LatLng(Double.valueOf(lati),Double.valueOf(longi));
                LatLng latLng1=new LatLng(Double.valueOf(mylati),Double.valueOf(mylongi));
                Double distance= SphericalUtil.computeDistanceBetween(latLng,latLng1);
                long dis = Math.round(distance);

                viewHolder.setDetails(getApplicationContext(), blog.getShopimage(),String.valueOf(dis), blog.getShopname(), blog.getShop());

            }
        };

            mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }
}
