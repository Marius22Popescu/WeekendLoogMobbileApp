package edu.bellevue.cs470.databasestorage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //create variable
    private ImageView iv_restaurant, iv_resorts, iv_beach, iv_culture, iv_playground;
    private ImageView iv_hiking, iv_nightlife, iv_winter, iv_park;
    private String tagType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign value to the image views
        iv_beach = (ImageView) findViewById(R.id.beach);
        iv_resorts = (ImageView) findViewById(R.id.resortsHotels);
        iv_restaurant = (ImageView) findViewById(R.id.restaurants);
        iv_culture = (ImageView) findViewById(R.id.culture);
        iv_park = (ImageView) findViewById(R.id.waterAmusementParks);
        iv_playground = (ImageView) findViewById(R.id.playgrounds);
        iv_hiking = (ImageView) findViewById(R.id.hikingLandscapes);
        iv_nightlife = (ImageView) findViewById(R.id.nightlife);
        iv_winter = (ImageView) findViewById(R.id.winterActivities);

        //set tags for each ImageView
        setTags();
        //Set listeners for the Image Views in order to open the child activity
        setListeners();

    }
    //this method will start the child activity
    private  void startChildActivity(){
        //Storing the Context in a variable
        Context context = MainActivity.this;
        // Creating the class that we want to start (and open) when the button is clicked
        Class destinationActivity = MainActivity2.class;
        //Create the Intent that will start the Activity we specified above
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        //putExtra method to put the String from the tag in the Intent
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, tagType);
        //start the ChildActivity
        startActivity(startChildActivityIntent);
    }
    //this method will set tags for each ImageView in order to manage them in data base
    private void setTags(){
        iv_beach.setTag("Beach");
        iv_culture.setTag("Culture");
        iv_hiking.setTag("Hiking");
        iv_nightlife.setTag("NightLife");
        iv_park.setTag("Park");
        iv_playground.setTag("Playground");
        iv_resorts.setTag("Resort");
        iv_restaurant.setTag("Restaurant");
        iv_winter.setTag("Winter");
    }
    //Set listeners for the Image Views in order to open the child activity
    private void setListeners(){
        iv_beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_hiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_nightlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_resorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
        iv_winter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagType = (String) view.getTag();
                startChildActivity();
            }
        });
    }
}
