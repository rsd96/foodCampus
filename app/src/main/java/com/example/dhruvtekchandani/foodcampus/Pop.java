package com.example.dhruvtekchandani.foodcampus;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by dhruvtekchandani on 2/9/18.
 */

public class Pop extends Activity{

    DatabaseReference restaurantAutumn;
    DatabaseReference restaurantSpring;
    DatabaseReference restaurantSummer;
    DatabaseReference restaurantWinter;
    DatabaseReference databaseRestaurantInformation;

    List<RestaurantYearlyTimings> autumnList;
    List<RestaurantYearlyTimings> springList;
    List<RestaurantYearlyTimings> summerList;
    List<RestaurantYearlyTimings> winterList;
    List<RestaurantInformation> restaurantTimings;

    Boolean summerCheack = true;
    Boolean winterCheck = false;
    Boolean sessionAutum = false;
    Boolean sessionSpring = false;
    String restaurantChosen = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseRestaurantInformation = FirebaseDatabase.getInstance().getReference("RestaurantInformation");
        restaurantAutumn = FirebaseDatabase.getInstance().getReference("RestaurantAutum");
        restaurantSpring = FirebaseDatabase.getInstance().getReference("RestaurantSpring");
        restaurantSummer = FirebaseDatabase.getInstance().getReference("RestaurantSummer");
        restaurantWinter = FirebaseDatabase.getInstance().getReference("RestaurantWinter");

        autumnList = new ArrayList<>();
        springList = new ArrayList<>();
        summerList = new ArrayList<>();
        winterList = new ArrayList<>();
        restaurantTimings = new ArrayList<>();

        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams xmp = getWindow().getAttributes();
        xmp.x = 0;
        xmp.y = -180;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.6),(int) (height*0.4));

        highlightToRed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        restaurantAutumn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autumnList.clear();
                for (DataSnapshot timingsSnapshots : dataSnapshot.getChildren()) {
                    RestaurantYearlyTimings yearlyTimingsList = timingsSnapshots.getValue(RestaurantYearlyTimings.class);
                    autumnList.add(yearlyTimingsList);
                }
                changeTimings();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        restaurantSpring.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                springList.clear();
                for(DataSnapshot timingsSnapshot: dataSnapshot.getChildren()){
                    RestaurantYearlyTimings yearlyTimingsList = timingsSnapshot.getValue(RestaurantYearlyTimings.class);
                    springList.add(yearlyTimingsList);
                }
                changeTimings();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        restaurantSummer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                summerList.clear();
                for(DataSnapshot timingsSnapshot: dataSnapshot.getChildren()){
                    RestaurantYearlyTimings yearlyTimingsList = timingsSnapshot.getValue(RestaurantYearlyTimings.class);
                    summerList.add(yearlyTimingsList);
                }
                changeTimings();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        restaurantWinter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                winterList.clear();
                for(DataSnapshot timingsSnapshot: dataSnapshot.getChildren()){
                    RestaurantYearlyTimings yearlyTimingsList = timingsSnapshot.getValue(RestaurantYearlyTimings.class);
                    winterList.add(yearlyTimingsList);
                }
                changeTimings();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseRestaurantInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantTimings.clear();
                for(DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    RestaurantInformation restaurantInformation = restaurantSnapshot.getValue(RestaurantInformation.class);
                    restaurantTimings.add(restaurantInformation);
                }
                changeTimings();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void highlightToRed(){
        TextView saturdayText = (TextView) findViewById(R.id.saturdayText);
        TextView sundayText = (TextView) findViewById(R.id.sundayText);
        TextView mondayText = (TextView) findViewById(R.id.mondayText);
        TextView tuesdayText = (TextView) findViewById(R.id.tuesdayText);
        TextView wednesdayText = (TextView) findViewById(R.id.wednesdayText);
        TextView thursdayText = (TextView) findViewById(R.id.thursdayText);
        TextView fridayText = (TextView) findViewById(R.id.fridayText);
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch(day){

            case 1:
                sundayText.setTextColor(Color.RED);
                break;

            case 2:
                mondayText.setTextColor(Color.RED);
                break;

            case 3:
                tuesdayText.setTextColor(Color.RED);
                break;

            case 4:
                wednesdayText.setTextColor(Color.RED);
                break;

            case 5:
                thursdayText.setTextColor(Color.RED);
                break;

            case 6:
                fridayText.setTextColor(Color.RED);
                break;

            case 7:
                saturdayText.setTextColor(Color.RED);
                break;
        }

    }

    public void changeTimings(){


        String monthResult = new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime());
        Calendar calender = Calendar.getInstance();
        int date = calender.get(Calendar.DATE);
        String dateString = String.valueOf(date);
        for(int i=0;i<autumnList.size();i++){
            final RestaurantYearlyTimings restaurantYearlyTimings = autumnList.get(i);
            String startDate = restaurantYearlyTimings.getStartDate() + "/" + restaurantYearlyTimings.getStartMonth();

            try {
                if (new SimpleDateFormat("d/MMMM").parse(startDate)
                        .before(new SimpleDateFormat("d/MMMM")
                                .parse(new SimpleDateFormat("d/MMMM").format(Calendar.getInstance().getTime())))){
                    Log.d("POP TIME ", "Autumn is after current date !!!!!!!" );

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dateString.equals(restaurantYearlyTimings.startDate) && monthResult.equals(restaurantYearlyTimings.startMonth)){
                sessionAutum = true;
                summerCheack = winterCheck = sessionSpring = false;
            }
        }

        for(int i=0;i<springList.size();i++){
            final RestaurantYearlyTimings restaurantYearlyTimings = springList.get(i);
            if(dateString.equals(restaurantYearlyTimings.startDate) && monthResult.equals(restaurantYearlyTimings.startMonth)){
                sessionAutum = false;
                winterCheck = false;
                sessionSpring = true;
                summerCheack = false;
            }
        }

        for(int i=0;i<summerList.size();i++){
            final RestaurantYearlyTimings restaurantYearlyTimings = summerList.get(i);
            if(dateString.equals(restaurantYearlyTimings.startDate) && monthResult.equals(restaurantYearlyTimings.startMonth)){
                sessionAutum = false;
                winterCheck = false;
                sessionSpring = false;
                summerCheack = true;
            }
        }

        for(int i=0;i<winterList.size();i++){
            final RestaurantYearlyTimings restaurantYearlyTimings = winterList.get(i);
            if(dateString.equals(restaurantYearlyTimings.startDate) && monthResult.equals(restaurantYearlyTimings.startMonth)){
                sessionAutum = false;
                winterCheck = true;
                sessionSpring = false;
                summerCheack = false;
            }
        }
        displayTimings();
    }

    public void displayTimings(){
        Bundle bundle = getIntent().getExtras();
        restaurantChosen = bundle.getString("RestaurantName");
        TextView saturdayText = (TextView) findViewById(R.id.saturdayText);
        TextView sundayText = (TextView) findViewById(R.id.sundayText);
        TextView mondayText = (TextView) findViewById(R.id.mondayText);
        TextView tuesdayText = (TextView) findViewById(R.id.tuesdayText);
        TextView wednesdayText = (TextView) findViewById(R.id.wednesdayText);
        TextView thursdayText = (TextView) findViewById(R.id.thursdayText);
        TextView fridayText = (TextView) findViewById(R.id.fridayText);

        for(int i =0; i<restaurantTimings.size() ;i++ ){
            final RestaurantInformation restaurantInformation = restaurantTimings.get(i);
            if(summerCheack == true){

                if(restaurantChosen.toString().equals(restaurantInformation.getRestName().toString())){
                    mondayText.setText("Mon: "+restaurantInformation.restTimingsOffSession);
                    tuesdayText.setText("Tue:  "+restaurantInformation.restTimingsOffSession);
                    wednesdayText.setText("Wed: "+restaurantInformation.restTimingsOffSession);
                    thursdayText.setText("Thu: "+restaurantInformation.restTimingsOffSession);
                    fridayText.setText("Fri:  "+restaurantInformation.restTimingsOffSession);
                    saturdayText.setText("Sat: "+restaurantInformation.restSaturdayTimings);
                    sundayText.setText("Sun: "+restaurantInformation.restSundayTimings);
                }
            }else if(winterCheck == true){
                if(restaurantChosen.toString().equals(restaurantInformation.getRestName().toString())){
                    mondayText.setText("Mon: "+restaurantInformation.restTimingsOffSession);
                    tuesdayText.setText("Tue:  "+restaurantInformation.restTimingsOffSession);
                    wednesdayText.setText("Wed: "+restaurantInformation.restTimingsOffSession);
                    thursdayText.setText("Thu: "+restaurantInformation.restTimingsOffSession);
                    fridayText.setText("Fri:  "+restaurantInformation.restTimingsOffSession);
                    saturdayText.setText("Sat: "+restaurantInformation.restSaturdayTimings);
                    sundayText.setText("Sun: "+restaurantInformation.restSundayTimings);
                }
            }else if(sessionAutum == true){
                    if(restaurantChosen.toString().equals(restaurantInformation.getRestName().toString())) {
                        mondayText.setText("Mon: " + restaurantInformation.restTimingsOnSession);
                        tuesdayText.setText("Tue:  " + restaurantInformation.restTimingsOnSession);
                        wednesdayText.setText("Wed: " + restaurantInformation.restTimingsOnSession);
                        thursdayText.setText("Thu: " + restaurantInformation.restTimingsOnSession);
                        fridayText.setText("Fri:  " + restaurantInformation.restTimingsOnSession);
                        saturdayText.setText("Sat: " + restaurantInformation.restSaturdayTimings);
                        sundayText.setText("Sun: " + restaurantInformation.restSundayTimings);
                    }
                }else if(sessionSpring == true){
                    if(restaurantChosen.toString().equals(restaurantInformation.getRestName().toString())) {
                        mondayText.setText("Mon: " + restaurantInformation.restTimingsOnSession);
                        tuesdayText.setText("Tue:  " + restaurantInformation.restTimingsOnSession);
                        wednesdayText.setText("Wed: " + restaurantInformation.restTimingsOnSession);
                        thursdayText.setText("Thu: " + restaurantInformation.restTimingsOnSession);
                        fridayText.setText("Fri:  " + restaurantInformation.restTimingsOnSession);
                        saturdayText.setText("Sat: " + restaurantInformation.restSaturdayTimings);
                        sundayText.setText("Sun: " + restaurantInformation.restSundayTimings);
                    }
                }
            }
        }
}
