package com.example.userprofile;

public class PollingController {
    private  MainActivity activity;
    int rate ;
    //timer
    //MEMORY
    GPSComunicationSystem gpsComunicationSystem;
    public PollingController(MainActivity Activity, int rate){
        activity = Activity;
        this.rate = rate;
        System.out.println("This is the polling rate : " + rate);
        gpsComunicationSystem = new GPSComunicationSystem();
        if(gpsComunicationSystem.initializeGPSCommunicationSystem(rate)){
            gpsComunicationSystem.setGPS(activity);
        }

    }

    public GPSComunicationSystem  getGpsComunicationSystem(){
        return gpsComunicationSystem;
    }

}
