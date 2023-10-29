package com.example.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    /**
     * Constructor to populate the data in the list.
     * @param context the current context used to inflate the layout file.
     * @param earthquakesList a list of earthquakes object to display in the list.
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakesList) {
        super(context, 0, earthquakesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }

        /**
         * Get the {@Link Earthquake} object located at this position in the list.
         */
        Earthquake currentEarthquake = getItem(position);

        //Find the TextView in the list_item.xml layout with the ID earthquake_magnitude.
        TextView earthquakeMagnitude =
                listItemView.findViewById(R.id.earthquake_magnitude);
        //Get the earthquake magnitude from the current Earthquake object
        //and set this text on the earthquakeMagnitude TextView.
        earthquakeMagnitude.setText(String.valueOf(currentEarthquake.getEarthquakeMagnitude()));

        //Find the TextView in the list_item.xml layout with the ID earthquake_place.
        TextView earthquakePlace =
                listItemView.findViewById(R.id.earthquake_place);
        //Get the earthquake place from the current Earthquake object
        //and set this text on the earthquakePlace TextView.
        earthquakePlace.setText(currentEarthquake.getEarthquakePlace());

        //Find the TextView in the list_item.xml layout with the ID earthquake_date.
        TextView earthquakeDate = listItemView.findViewById(R.id.earthquake_date);

        //Wrapping UNIX time in milliseconds in date object.
        Date date = new Date(currentEarthquake.getEarthquakeTime());
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(date);
        //Set this text on the earthquakeTime TextView.
        earthquakeDate.setText(formattedDate);

        //Find the TextView in the list_item.xml layout with the ID earthquake_time.
        TextView  earthquakeTime = listItemView.findViewById(R.id.earthquake_time);
        //Format the date string (i.e. "4:30PM)
        String formattedTime = formatTime(date);
        //Display the time of the current earthquake in that Textview.
        earthquakeTime.setText(formattedTime);

        //Return the whole list item layout (containing 3 TextView)
        //so that it can be shown in the ListView.
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return simpleDateFormat.format(date);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(date);
    }
}
