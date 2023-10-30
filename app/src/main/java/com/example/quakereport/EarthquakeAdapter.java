package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

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
        earthquakeMagnitude.setText(formatMagnitude(currentEarthquake.getEarthquakeMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) earthquakeMagnitude.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getEarthquakeMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Storing the location in a string.
        String currentLocation = currentEarthquake.getEarthquakePlace();
        //Variables to store the locations after splitting.
        String locationOffset, locationPrimary;
        //Checking if the location contains the "of" in the string.
        if(currentLocation.contains(LOCATION_SEPARATOR)) {
            //If it does, split the string into 2 halves and separate them
            //in two different variables to display separately.
            String[] bothLocations = currentLocation.split(LOCATION_SEPARATOR);
            locationOffset = bothLocations[0] + LOCATION_SEPARATOR;
            locationPrimary = bothLocations[1];
        }else {
            //If it doesn't contains the "of", just store "Near the" as offset
            //and rest of the location string is stored as primary location.
            locationOffset = getContext().getString(R.string.near_the);
            locationPrimary = currentLocation;
        }

        //Find the TextView in the list_item.xml layout with the ID earthquake_place.
        TextView earthquakeLocationOffset = listItemView.findViewById(R.id.location_offset);
        //Get the earthquake place from the current Earthquake object
        //and set this text on the earthquakePlace TextView.
        earthquakeLocationOffset.setText(locationOffset);

        TextView earthquakeLocationPrimary = listItemView.findViewById(R.id.location_primary);
        earthquakeLocationPrimary.setText(locationPrimary);

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

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceID;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch(magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceID = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceID = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceID = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceID = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceID = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceID = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceID = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceID = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceID = R.color.magnitude9;
                break;
            default: magnitudeColorResourceID = R.color.magnitude10plus;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceID);
    }
}
