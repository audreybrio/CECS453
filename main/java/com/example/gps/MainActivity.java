package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    Button btnShowLocation;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Location_Tracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        btnShowLocation = (Button) findViewById(R.id.button);
        // we will show the location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // we will create a class object
                gps = new Location_Tracker(MainActivity.this);
                // Now we will check if GPS is enabled
                if (gps.isGPSEnabled) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(), "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_LONG).show();
                }
                else {
                    gps.showSettings();
                }
            }
        });
    }
}
