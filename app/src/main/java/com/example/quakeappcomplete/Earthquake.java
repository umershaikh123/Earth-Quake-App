package com.example.quakeappcomplete;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Earthquake {

    private final double magnitude;
    private String stringMagnitude;
    private final String city;
    private final String url;
    private final String location;
    private final String date;
    private final String time;





    public Earthquake(double mag , String location, String city , String date , String time , String url ){
        this.magnitude = mag;
        this.location = location;
        this.city = city;
        this.date = date;
        this.time = time;
        this.url = url;
    }


//    public Earthquake(String mag ,  String city , String date   ){
//        this.magnitude = mag;
//        this.city = city;
//        this.date = date;
//    }



    public double getMagnitude(){
        return magnitude;
    }

    public String getCity(){
        return city;
    }

    public String getLocation(){
        return location;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return  date ;
    }

    public String getUrl(){
        return url;
    }


    public String getStringMagnitude(){
        stringMagnitude = ""+magnitude;
        return stringMagnitude;
    }



}//MC
