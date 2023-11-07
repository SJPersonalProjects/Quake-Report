package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

    //ListView for to display the list of earthquakes.
    private ListView listView;
    //TextView for to display the empty state of the app.
    private TextView emptyViewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a reference to the {@link Listview} in the layout.
        listView = findViewById(R.id.list_view);

        //Find a reference to the {@link TextView} in the layout.
        emptyViewTextView = findViewById(R.id.empty_view_text_view);


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

        //Get a reference to the ConnectivityManager to check state of the network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network.
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //If there's network connection, fetch data.
        if(networkInfo != null && networkInfo.isConnected()) {
            //Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            //Initialize the loader. Pass in the int ID constant defined above and pass
            //in null for the bundle. Pass in this activity for the loadercallbacks parameter
            //(which is valid because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else {
            //Otherwise, display the error.
            //First, hide the loading indicator so error message will be visible.
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            //Update the empty state with no connection error message.
            emptyViewTextView.setText(R.string.no_internet_connection);
        }
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

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);



        //Clear the adapter of previous earthquake data.
        mAdapter.clear();

        //If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        //data set. This will trigger the listview to update.
        if(earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }else {
            // Set empty state text to display "No earthquakes found."
            emptyViewTextView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        Log.e(LOG_TAG, "onLoaderReset callback");
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}