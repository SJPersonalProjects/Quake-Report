package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /**
     * Tag for the log message.
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * URL to query the USGS dataset for earthquakes information.
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=1&maxmag=20&limit=50";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play, if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //Adapter for the list of earthquakes.
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a reference to the {@link Listview} in the layout.
        ListView listView = findViewById(R.id.list_view);

        //Create a new {@Link EarthquakeAdapter} whose data source is a list of
        //{@Link Earthquake}s. The adapter knows how to create list item views for
        //each item in the list.
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        //Set the adapter on the {@Link ListView}
        //so the list can be populated on the user interface.
        listView.setAdapter(mAdapter);

        //Set an item click listener on the ListView, which sends an intent to a web browser
        //to open a website with more information about the selected earthquake.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getEarthquakeUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //Initialize the loader. Pass in the int ID constant defined above and pass
        //in null for the bundle. Pass in this activity for the loadercallbacks parameter
        //(which is valid because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e(LOG_TAG, "onCreateLoader() callback");

        //Create a new loader for a given url.
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.e(LOG_TAG, "onLoadFinished() callback");
        //Clear the adapter of previous earthquake data.
        mAdapter.clear();

        //If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        //data set. This will trigger the listview to update.
        if(earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        Log.e(LOG_TAG, "onLoaderReset callback");
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}