package fr.jpsave.android.mymeteo.model;

public class City {
    private String mName;
    private String mDescription;
    private String mTemperature;
    private int mWeatherIcon;
    private double mLon;
    private double mLat;
    private int mIdCity;

    public City(String mName, String mDescription, String mTemperature, int mWeatherIcon) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTemperature = mTemperature;
        this.mWeatherIcon = mWeatherIcon;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public int getmWeatherIcon() {
        return mWeatherIcon;
    }

    public double getmLon() {
        return mLon;
    }

    public void setmLon(double mLon) {
        this.mLon = mLon;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public int getmIdCity() {
        return mIdCity;
    }

    public void setmIdCity(int mIdCity) {
        this.mIdCity = mIdCity;
    }

    @Override
    public String toString() {
        return "City{" +
                "mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mTemperature='" + mTemperature + '\'' +
                ", mWeatherIcon=" + mWeatherIcon +
                ", mLon=" + mLon +
                ", mLat=" + mLat +
                ", mIdCity=" + mIdCity +
                '}';
    }
}
