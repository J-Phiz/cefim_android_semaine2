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
import fr.jpsave.android.mymeteo.model.CityWidget;

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


    public static <T> void savePreferences(String prefName, Context context, ArrayList<T> prefs) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < prefs.size(); i++) {
            Gson gson = new Gson();
            jsonArray.put(gson.toJson(prefs.get(i)));
        }
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Log.i("MyMeteoDebug", "savePreferences: " + jsonArray.toString());
        editor.putString(prefName, jsonArray.toString());
        editor.apply();
    }

    public static <T> ArrayList<T> loadPreferences(String prefName, Context context, Class<T> deserialiseClass) {
        ArrayList<T> prefs = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(prefName, ""));
            Log.i("MyMeteoDebug", "loadPreferences: " + jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                Gson gson = new Gson();
                T pref = (gson.fromJson(jsonArray.getString(i), deserialiseClass));
                prefs.add(pref);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return prefs;
    }
}
