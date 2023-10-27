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
        ArrayList<String> earthquakesList = new ArrayList<>();
        earthquakesList.add("San Francisco");
        earthquakesList.add("London");
        earthquakesList.add("Tokyo");
        earthquakesList.add("Mexico City");
        earthquakesList.add("Moscow");
        earthquakesList.add("Rio de Janeiro");
        earthquakesList.add("Paris");

        //Find a reference to the {@link Listview} in the layout.
        ListView listView = findViewById(R.id.list_view);

        //Create a new {@Link ArrayAdapter} of earthquakes.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, earthquakesList);

        //Set the adapter on the {@Link ListView}
        //so the list can be populated on the user interface.
        listView.setAdapter(adapter);

    }
}