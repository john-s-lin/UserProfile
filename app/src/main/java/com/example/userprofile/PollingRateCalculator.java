package com.example.userprofile;

import android.content.SharedPreferences;
import android.widget.NumberPicker;

public class PollingRateCalculator {
    private int rate;
    private String sport;
    NumberPicker gpsNumberPicker;
    private SharedPreferences preferences;
    private static final String FREQ_PROGRESS = "freq_progress";

    public PollingRateCalculator(){
        rate = 10;
        sport = "bike";
    }
    //TODO implement the access to the memory to retrieve the sports and the battery saving mode.
    void getParameter(){

    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int requestPolingRate(){
        return rate;
    }

}
