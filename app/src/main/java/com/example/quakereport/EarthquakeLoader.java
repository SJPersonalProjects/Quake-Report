package com.example.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag for log messages. */
    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    /** Query URL */
    private String mUrl;


    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e(LOG_TAG, "onStartLoading() method");
    }

    /**
     * This is on a background thread.
     */
    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG, "loadInBackground() method");
        if(mUrl == null) {
            return null;
        }

        //Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
