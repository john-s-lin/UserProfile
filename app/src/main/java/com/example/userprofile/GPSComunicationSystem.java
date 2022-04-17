package com.example.userprofile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPSComunicationSystem {

    private MainActivity GPS;   //link to the MainActivity to be able to call the location.
    // Locations request is a config file for settings related to  FuselLocationProviderClient
    LocationRequest locationRequest;



    public GPSComunicationSystem(){
     System.out.println("I am in the GPS COMMUNICATION SYSRTEM");
    }

    public boolean initializeGPSCommunicationSystem(int rate){

        // TODO implement a better logic for the rate and battery saving;
        System.out.println("I am in the function INITIALIZEGPSCOMUNICATIONSYSTEM");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(rate);
        locationRequest.setFastestInterval(rate);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return true;
    }


    public void setGPS(MainActivity mainGPS){
      GPS = mainGPS  ;
      System.out.println("IN the setGPS function");
      GPS.getLocation();
    };

    public void passLocation(Location location){
        System.out.println("In the pass Location function the longitude is : " + location.getLongitude());
    }






}
