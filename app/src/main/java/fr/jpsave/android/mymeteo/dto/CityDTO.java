package fr.jpsave.android.mymeteo.dto;

import com.google.gson.annotations.SerializedName;

public class CityDTO {
    public static class Coord {
        public double lon;
        public double lat;
        public Coord(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;
        }
    }
    public Coord coord;
    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
        public Weather(int id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }
    }
    public Weather[] weather;
    public String base;
    public static class MainW {
        public float temp;
        @SerializedName("feels_like")
        public float tempFeels;
        @SerializedName("temp_min")
        public float tempMin;
        @SerializedName("temp_max")
        public float tempMax;
        public int pressure;
        public int humidity;
        public MainW(float temp, float tempFeels, float tempMin, float tempMax, int pressure, int humidity) {
            this.temp = temp;
            this.tempFeels = tempFeels;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.pressure = pressure;
            this.humidity = humidity;
        }
    }
    public MainW main;
    public int visibility;
    public static class Wind {
        public float speed;
        public int deg;
        public Wind(float speed, int deg) {
            this.speed = speed;
            this.deg = deg;
        }
    }
    public Wind wind;
    public static class Cloud {
        public int all;
        public Cloud(int all) {
            this.all = all;
        }
    }
    public Cloud clouds;
    public int dt;
    public static class Sys {
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
        public Sys(int type, int id, String country, int sunrise, int sunset) {
            this.type = type;
            this.id = id;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }
    }
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
    public String message;
}
