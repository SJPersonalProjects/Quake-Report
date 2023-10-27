package com.example.quakereport;

public class Earthquake {
    //Instance fields.
    private final String mEarthquakeMagnitude;
    private final String mEarthquakePlace;
    private final String mEarthquakeTime;

    /**
     * Constructs an object of type Earthquake.
     * @param mEarthquakeMagnitude to store magnitude of earthquake.
     * @param mEarthquakePlace to store place of earthquake.
     * @param mEarthquakeTime to store the time of when earthquake occurred.
     */
    public Earthquake(String mEarthquakeMagnitude, String mEarthquakePlace,
                      String mEarthquakeTime) {
        this.mEarthquakeMagnitude = mEarthquakeMagnitude;
        this.mEarthquakePlace = mEarthquakePlace;
        this.mEarthquakeTime = mEarthquakeTime;
    }

    /**
     * Magnitude of the current earthquake.
     * @return earthquake magnitude
     */
    public String getEarthquakeMagnitude() {
        return mEarthquakeMagnitude;
    }

    /**
     * Place of the current earthquake.
     * @return earthquake place.
     */
    public String getEarthquakePlace() {
        return mEarthquakePlace;
    }

    /**
     * Time of the current earthquake.
     * @return earthquake time.
     */
    public String getEarthquakeTime() {
        return mEarthquakeTime;
    }
}
