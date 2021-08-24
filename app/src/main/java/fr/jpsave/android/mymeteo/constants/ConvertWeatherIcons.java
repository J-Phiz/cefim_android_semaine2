package fr.jpsave.android.mymeteo.constants;

import java.util.HashMap;
import fr.jpsave.android.mymeteo.R;

public class ConvertWeatherIcons {
    private static HashMap<Integer, Integer> whiteTable;
    private static HashMap<Integer, Integer> greyTable;
    static {
        whiteTable = new HashMap<>();
        whiteTable.put(1, R.drawable.weather_sunny_white);
        whiteTable.put(2, R.drawable.weather_cloudy_white);
        whiteTable.put(3, R.drawable.weather_foggy_white);
        whiteTable.put(4, R.drawable.weather_cloudy_white);
        whiteTable.put(9, R.drawable.weather_rainy_white);
        whiteTable.put(10, R.drawable.weather_rainy_white);
        whiteTable.put(11, R.drawable.weather_thunder_white);
        whiteTable.put(13, R.drawable.weather_snowy_white);
        whiteTable.put(50, R.drawable.weather_foggy_white);

        greyTable = new HashMap<>();
        greyTable.put(1, R.drawable.weather_sunny_grey);
        greyTable.put(2, R.drawable.weather_cloudy_grey);
        greyTable.put(3, R.drawable.weather_foggy_grey);
        greyTable.put(4, R.drawable.weather_cloudy_grey);
        greyTable.put(9, R.drawable.weather_rainy_grey);
        greyTable.put(10, R.drawable.weather_rainy_grey);
        greyTable.put(11, R.drawable.weather_thunder_grey);
        greyTable.put(13, R.drawable.weather_snowy_grey);
        greyTable.put(50, R.drawable.weather_foggy_grey);
    }

    public enum IconColor {
        GREY, WHITE
    }

    public static int getIcon(int iconId, IconColor color) {
        HashMap<Integer, Integer> table;
        if(color == IconColor.GREY) {
            table = greyTable;
        } else {
            table = whiteTable;
        }

        return table.get(iconId) != null ? table.get(iconId) : 0;
    }
}
