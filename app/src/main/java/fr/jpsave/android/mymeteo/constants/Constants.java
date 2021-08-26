package fr.jpsave.android.mymeteo.constants;

import java.util.Locale;

public class Constants {
    public static final String MSG_MAPS_ACT = "msgMapsActKey";
    public static final String MSG_MAIN_ACT = "msgMainActKey";
    public static final String WEATHER_API_URL_WITH_COMMON_OPTS =
            "http://api.openweathermap.org/data/2.5/weather" +
            "?APPID=" + APIKeys.WEATHER_API_KEY +
            "&units=metric" +
            "&lang=" + Locale.getDefault().getLanguage();
    public static final String PREFS_NAME = "myMeteoPrefs";
    public static final String PREFS_FAVORITE_CITIES = "myMeteoPrefsFavoriteCities";
}
