package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;

//Group 14 imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    LanguageManager lang;
    //THE following property are related to the GPS function / object
    final int PERMISSONS_FINE_LOCATION = 99;
    FusedLocationProviderClient fusedLocationProviderClient ;
    //Main function property
    GPSComunicationSystem gpsCommSystem;
    PollingController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("general_settings", MODE_PRIVATE);
        String selectedLanguage = prefs.getString("lang_code", "en");
        lang = new LanguageManager(this);
        lang.updateResource(selectedLanguage);
        setContentView(R.layout.activity_main);


        controller = new PollingController(this, prefs.getInt("freq_progress", 0));
        gpsCommSystem = controller.getGpsComunicationSystem();
        testingGPS();

        // Set title
        getSupportActionBar().setTitle("User Settings");

        // Open create profile page
        Button createButton = findViewById(R.id.createProfileButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
                startActivity(intent);
            }
        });

        Button viewButton = findViewById(R.id.viewProfileButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayProfileActivity.class);
                startActivity(intent);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = getFilesDir();
                File myFile = new File(dir, CreateProfileActivity.PROFILE_FILE_NAME);
                if (myFile.delete()) {
                    Toast.makeText(MainActivity.this,"File Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,"File does not exist", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button settingsBtn = findViewById(R.id.generalSettingsButton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new intent to open settings activity.
                Intent i = new Intent(MainActivity.this, GeneralSettings.class);
                startActivity(i);
            }
        });


    }

    // accessing the permission to use the GPS is failed close the program
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("I am in the On requiest permisionsResult function");
        switch(requestCode){
            case PERMISSONS_FINE_LOCATION:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    // updateGPS(); TODO change that to call the poling timer;

                }else{
                    Toast.makeText(this, "This app required permission to be granted in order to work properly",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    // This part is the GPS OBJECT FROM THE system Sequence Diagram

    public  void testingGPS(){
        System.out.println("I am in testing GPS");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got the permisions  put value of location into UI
                    //updateUIValues(location);
                    System.out.println("This is the altitude when initializing the GPS" + location.getAltitude());
                }
            });


        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSONS_FINE_LOCATION);
            }
        }
    };

    public  void getLocation(){
        System.out.println("I am in testing GPS");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got the permisions  put value of location into UI
                    //updateUIValues(location);
                    gpsCommSystem.passLocation(location);
                    //System.out.println("This is the altitude when initializing the GPS" + location.getAltitude());
                }
            });


        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSONS_FINE_LOCATION);
            }
        }
    };


}
