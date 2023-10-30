package com.example.quakereport;

public class Earthquake {
    //Instance fields.
    private final double mEarthquakeMagnitude;
    private final String mEarthquakePlace;
    private final long mEarthquakeTime;
    private final String mEarthquakeUrl;

    /**
     * Constructs an object of type Earthquake.
     * @param mEarthquakeMagnitude to store magnitude of earthquake.
     * @param mEarthquakePlace to store place of earthquake.
     * @param mEarthquakeTime to store the time of when earthquake occurred.
     */
    public Earthquake(double mEarthquakeMagnitude, String mEarthquakePlace,
                      long mEarthquakeTime, String mEarthquakeUrl) {
        this.mEarthquakeMagnitude = mEarthquakeMagnitude;
        this.mEarthquakePlace = mEarthquakePlace;
        this.mEarthquakeTime = mEarthquakeTime;
        this.mEarthquakeUrl = mEarthquakeUrl;
    }

    /**
     * Magnitude of the current earthquake.
     * @return earthquake magnitude
     */
    public double getEarthquakeMagnitude() {
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
    public long getEarthquakeTime() {
        return mEarthquakeTime;
    }

    /**
     * Url of the current earthquake.
     * @return earthquake url
     */
    public String getEarthquakeUrl() {
        return mEarthquakeUrl;
    }
}
