package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    Button btnShowLocation;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String destinationAddress;
    Location_Tracker gps;
    FusedLocationProviderClient fusedLocationClient;
    TravelManager manager = new TravelManager(); //NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
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
                } else {
                    gps.showSettings();
                }
            }
        });
    }
    public void calcdestination(View view) {
        EditText location = (EditText) findViewById(R.id.location);
        String address = location.getText().toString();
        boolean goodGeoCoding = true;
        if (!address.equals(destinationAddress)) {
            destinationAddress = address;
            Geocoder coder = new Geocoder(this );
            try {
                // geocode destination
                List<Address> addresses = coder.getFromLocationName(destinationAddress, 5);
                if (addresses != null) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();
                    Location destinationLocation = new Location("destination");
                    Log.e("Destination Location", "" + latitude + ", " + longitude);
                    destinationLocation.setLatitude(latitude);
                    destinationLocation.setLongitude(longitude);
                    manager.setDestination(destinationLocation);
                }
            } catch (IOException ioe) {
                goodGeoCoding = false;
            }
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location mGetedLocation = task.getResult();
                            Log.e("My Location", "" + mGetedLocation.getLatitude() + ", " + mGetedLocation.getLongitude());
                            double currentLat = mGetedLocation.getLatitude();
                            double currentLng = mGetedLocation.getLongitude();
                            String miles = manager.milesToDestination(mGetedLocation);
                            TextView txt = (TextView) findViewById(R.id.distance);
                            txt.setText("Total Distance:" + miles);
                            //Toast.makeText(getApplicationContext(), "This is distance:" + miles, Toast.LENGTH_LONG).show();
                            //updateUI();
                        } else {
                            Log.e("TAG", "no location detected");
                            Log.w("TAG", "getLastLocation:exception",
                                    task.getException());
                        }
                    }
                });
    }
}
