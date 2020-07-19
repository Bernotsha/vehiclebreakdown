package com.bernotsha.vehiclebreakdown;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Button registerbutton,mobileadd;
    TextView registermobile;
    public static int RESOLVE_HINT=1011;
    String mobNumber;
    DatabaseReference reference;
    String name,mobile,email,address,licence,password,confirmpassword;
    EditText registername,registeremail,registeraddress,registerlicence,registerpassword,registerconfirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("REGISTRATION");
        reference= FirebaseDatabase.getInstance().getReference();
        registerbutton=findViewById(R.id.registerbutton);
        registermobile=findViewById(R.id.registermobile);
        registername=findViewById(R.id.registername);
        registeremail=findViewById(R.id.registeremail);
        registeraddress=findViewById(R.id.registeraddress);
        registerlicence=findViewById(R.id.registerlicence);
        registerpassword=findViewById(R.id.registerpassword);
        registerconfirmpassword=findViewById(R.id.registerconfirmpassword);
        mobileadd=findViewById(R.id.mobileadd);
        mobileadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getphone();
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=registername.getText().toString();
                mobile=registermobile.getText().toString();
                email=registeremail.getText().toString();
                address=registeraddress.getText().toString();
                licence=registerlicence.getText().toString();
                password=registerpassword.getText().toString();
                confirmpassword=registerconfirmpassword.getText().toString();
                if(name.isEmpty())
                {
                    registername.setError("Required");
                    registername.requestFocus();
                    return;
                }
                else if(mobile.isEmpty())
                {
                    registermobile.setError("Mobile");
                    registermobile.requestFocus();
                    return;
                }
                else if(email.isEmpty())
                {
                    registeremail.setError("Required");
                    registeremail.requestFocus();
                    return;
                }
                else if(address.isEmpty())
                {
                    registeraddress.setError("Required");
                    registeraddress.requestFocus();
                    return;
                }
                else if(licence.isEmpty())
                {
                    registerlicence.setError("Required");
                    registerlicence.requestFocus();
                    return;
                }
                else if(password.isEmpty())
                {
                    registerpassword.setError("Required");
                    registerpassword.requestFocus();
                    return;
                }
                else if(confirmpassword.isEmpty())
                {
                    registerconfirmpassword.setError("Required");
                    registerconfirmpassword.requestFocus();
                    return;
                }
                else if(!confirmpassword.equals(password))
                {
                    registerconfirmpassword.setError("Not Matched");
                    registerconfirmpassword.requestFocus();
                    return;
                }
                else
                {
                    enrolluser();
                    Intent i = new Intent(register.this,prerequiste.class);
                    startActivity(i);
                    SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor =prefs.edit();
                    editor.putBoolean("firststart",true);
                    editor.apply();
                    finish();
                }

            }
        });
    }
    public void enrolluser()
    {
        Enroll enroll=new Enroll(name,mobile,email,address,licence,password,confirmpassword);
        reference.child("Registration").child(mobile).setValue(enroll);

    }

    private void getphone() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) register.this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) register.this)
                .build();
        googleApiClient.connect();
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.auth.api.credentials.Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    mobNumber = credential.getId();
                    String newString = mobNumber.replace("+91", "");
                    registermobile.setText(newString);

                } else {
                    Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(register.this,login.class);
        startActivity(i1);
        finish();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

