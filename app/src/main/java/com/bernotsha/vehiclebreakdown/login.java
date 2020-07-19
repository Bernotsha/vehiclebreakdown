package com.bernotsha.vehiclebreakdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    TextView register1;
    Button loginbutton;
    EditText loginmobile,loginpassword;
    TextView invalid;
    String mobile1,password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("LOGIN");
        loginmobile=findViewById(R.id.loginmobile);
        loginpassword=findViewById(R.id.loginpassword);
        register1=findViewById(R.id.register1);
        loginbutton=findViewById(R.id.loginbutton);
        invalid=findViewById(R.id.invalid);
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class));
                finish();
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile1=loginmobile.getText().toString();
                password1=loginpassword.getText().toString();
                if(mobile1.isEmpty())
                {
                    loginmobile.setError("Required");
                    loginmobile.requestFocus();
                    return;
                }
                else if(password1.isEmpty())
                {
                    loginpassword.setError("Required");
                    loginpassword.requestFocus();
                    return;
                }
                else
                {
                    checkuser();
                }
            }
        });
    }
    public void checkuser()
    {
        FirebaseDatabase.getInstance().getReference("Registration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Getuser getuser=snapshot.getValue(Getuser.class);
                    assert getuser != null;
                    if(getuser.mobile.equals(mobile1) && getuser.password.equals(password1))
                    {
                        Intent i = new Intent(login.this,prerequiste.class);
                        startActivity(i);
                        finish();
                        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
                        SharedPreferences.Editor editor =prefs.edit();
                        editor.putBoolean("firststart",true);
                        editor.apply();
                    }
                    else
                    {
                        invalid.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}