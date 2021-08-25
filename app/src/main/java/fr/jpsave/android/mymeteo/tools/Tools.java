package fr.jpsave.android.mymeteo.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.model.City;

public class Tools {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Oui il y a Internet
            Log.i(context.getClass().getName(), "Internet Access OK");
            return true;
        } else {
            // Non... J’affiche un message à l’utilisateur
            Log.e(context.getClass().getName(), "No Internet Access");
            return false;
        }
    }

    public static String formatCityName(String cityName) {
        return cityName.substring(0,1).toUpperCase() + cityName.substring(1).toLowerCase();
    }

    public static void saveFavoriteCities(Context context, ArrayList<City> cities) {
        JSONArray jsonArrayCities = new JSONArray();
        for (int i = 0; i < cities.size(); i++) {
            Gson gson = new Gson();
            jsonArrayCities.put(gson.toJson(cities.get(i)));
        }
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Log.i("MyMeteoDebug", "saveFavoriteCities: " + jsonArrayCities.toString());
        editor.putString(Constants.PREFS_FAVORITE_CITIES, jsonArrayCities.toString());
        editor.apply();
    }

    public static ArrayList<City> initFavoriteCities(Context context) {
        ArrayList<City> cities = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);

        try {
            JSONArray jsonArrayCities = new JSONArray(preferences.getString(Constants.PREFS_FAVORITE_CITIES, ""));
            Log.i("MyMeteoDebug", "initFavoriteCities: " + jsonArrayCities.toString());
            for (int i = 0; i < jsonArrayCities.length(); i++) {
                Gson gson = new Gson();
                City city = (gson.fromJson(jsonArrayCities.getString(i), City.class));
                cities.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
