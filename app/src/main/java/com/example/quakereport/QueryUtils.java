package com.example.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public class QueryUtils {
    /**
     * Tag for the log message.
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Create a new URL object from a given string.
     * @param stringURL is the url of type string.
     * @return url
     */
    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        }catch(MalformedURLException malformedURLException) {
            Log.e(LOG_TAG, "Problem building the URL", malformedURLException);
        }

        return url;
    }

    /**
     * Make an HTTP request to the given URL and return the String as a json response.
     * @param url is of type URL object used to extract the json response
     * @return the String in the form of json response
     * @throws IOException
     */
    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        //If url is null, return early.
        if(url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /*milliseconds*/);
            httpURLConnection.setConnectTimeout(15000 /*milliseconds*/);
            httpURLConnection.connect();

            //If the request was successful, (response code 200)
            //then read the input stream and parse the response.
            if(httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                System.out.println(httpURLConnection.getResponseCode() +
                        "_______________________________________________________________________");
            }else {
                Log.e(LOG_TAG, "HTTP response code: " + httpURLConnection.getResponseCode());
                System.out.println(httpURLConnection.getResponseCode() +
                        "_______________________________________________________________________");
            }
        }catch(IOException ioException) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results." + ioException);
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null) //Function must handle java.io.IOException here.
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new
                    InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Earthquake> extractFeaturesFromJson(String earthquakeJSON) {

        //If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        //Create an empty ArrayList that we can start adding earthquakes to.
        List<Earthquake> earthquakes = new ArrayList<>();

        //Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        //is formatted, a JSONException exception object will be thrown.
        //Catch the exception so that the app doesn't crash, and print the error message
        //to the logs.
        try {

            //Initializing the JSSONObject and extracting the information.
            JSONObject jsonRootObject = new JSONObject(earthquakeJSON);
            //Extract the JSONArray associated with the key called "features",
            //which represents a list of features (or earthquakes).
            JSONArray features = jsonRootObject.getJSONArray("features");

            //For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for(int i = 0; i < features.length(); i++) {

                //Get a single earthquake at position i within the list of earthquakes
                JSONObject jsonObject = features.getJSONObject(i);

                //For a given earthquake, extract the JSONObject associated with the
                //key called "properties", which represents a list of all properties
                //for that earthquake.
                JSONObject properties = jsonObject.getJSONObject("properties");

                //Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                //Extract the value for the key called "place"
                String place = properties.getString("place");

                //Extract the value for the key called "time"
                long time = properties.getLong("time");

                //Extract the value for the key called "url"
                String url = properties.getString("url");

                //Create a new {@link Earthquake} object with the magnitude, location, time,
                //and url from the JSON response.
                //Add the new {@link Earthquake} to the list of earthquakes.
                earthquakes.add(new Earthquake(magnitude, place, time, url));
            }
        }catch(JSONException exception) {
            //If an error is thrown when executing any of the above statements in the "try" block,
            //catch the exception here, so the app doesn't crash. Print a log message
            //with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", exception);
        }

        //Return the list of earthquakes.
        return earthquakes;
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestURL) {
        Log.e(LOG_TAG, "fetchEarthquakeData() method");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Create URL object.
        URL url = createURL(requestURL);

        //Perform HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        }catch(IOException ioException) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", ioException);
        }

        //Extract relevant fields from the JSON response and create a list of {@link Earthquake}s.
        List<Earthquake> earthquakes = extractFeaturesFromJson(jsonResponse);

        //Return the list of {@link Earthquake}s.
        return earthquakes;
    }
}
