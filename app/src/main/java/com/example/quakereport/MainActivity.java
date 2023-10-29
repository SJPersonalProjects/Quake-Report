package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakesList = QueryUtils.extractEarthquakes();
//        earthquakesList.add(new Earthquake("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakesList.add(new Earthquake("6.1", "London", "July 20, 2015"));
//        earthquakesList.add(new Earthquake("3.9", "Tokyo", "Nov 10, 2014"));
//        earthquakesList.add(new Earthquake("5.4", "Mexico City", "May 3, 2014"));
//        earthquakesList.add(new Earthquake("2.8", "Moscow", "Jan 31, 2013"));
//        earthquakesList.add(new Earthquake("4.9", "Rio de Janeiro", "Aug 19, 2012"));
//        earthquakesList.add(new Earthquake("1.6", "Paris", "Oct 30, 2011"));

        //Find a reference to the {@link Listview} in the layout.
        ListView listView = findViewById(R.id.list_view);

        //Create a new {@Link EarthquakeAdapter} whose data source is a list of
        //{@Link Earthquake}s. The adapter knows how to create list item views for
        //each item in the list.
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakesList);

        //Set the adapter on the {@Link ListView}
        //so the list can be populated on the user interface.
        listView.setAdapter(adapter);

    }
}