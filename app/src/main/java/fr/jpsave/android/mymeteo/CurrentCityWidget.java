package fr.jpsave.android.mymeteo;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.activity.FavoriteActivity;
import fr.jpsave.android.mymeteo.client.ClientAPI;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.dto.CityDTO;
import fr.jpsave.android.mymeteo.mapper.CityMapper;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.model.CityWidget;
import fr.jpsave.android.mymeteo.tools.Tools;

/**
 * Implementation of App Widget functionality.
 */
public class CurrentCityWidget extends AppWidgetProvider implements ClientAPI {

    private Context mContext;
    private AppWidgetManager mAppWidgetManager;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        mContext = context;
        mAppWidgetManager = appWidgetManager;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.current_city_widget);
//        views.setTextViewText(R.id.tv_widget_temp, String.valueOf(appWidgetId));

        ArrayList<CityWidget> cityWidgets = Tools.loadPreferences(Constants.PREFS_WIDGETS_CITY, context, CityWidget.class);
        City currentCity = null;

        for (CityWidget cityWidget: cityWidgets) {
            if (cityWidget.getWidgetId() == appWidgetId) {
                currentCity = cityWidget.getCity();
                break;
            }
        }

        if(currentCity != null) {
            callAPI(
                    this,
                    context,
                    Constants.WEATHER_API_URL_WITH_COMMON_OPTS +
                            "&q=" + currentCity.getmName(),
                    appWidgetId
            );
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    new ContextThemeWrapper(mContext, R.style.Theme_MyMeteo_AlertDialog)
            );
            View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
            final EditText newCityName = v.findViewById(R.id.edit_text_add_favorite);
            builder.setView(v);

            builder.setNegativeButton(R.string.add_favorite_button_cancel, null);
            builder.setPositiveButton(R.string.add_favorite_button_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    callAPI(
                            CurrentCityWidget.this,
                            mContext,
                            Constants.WEATHER_API_URL_WITH_COMMON_OPTS +
                                    "&q=" + newCityName.getText().toString(),
                            null
                    );
                }
            });
        }



        // Setup update button to send an update request as a pending intent.
        Intent intentUpdate = new Intent(context, CurrentCityWidget.class);

        // The intent action must be an app widget update.
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // Include the widget ID to be updated as an intent extra.
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        // Wrap it all in a pending intent to send a broadcast.
        // Use the app widget ID as the request code (third argument) so that
        // each intent is unique.
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,
                appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        // Assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.ib_refresh, pendingUpdate);





        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAPIFailure() {

    }

    @Override
    public void onAPISuccess(String json, Integer id) {
        boolean found = false;
        City city;
        Gson gson = new Gson();
        CityDTO cityDto = gson.fromJson(json, CityDTO.class);
        if (cityDto != null && cityDto.cod == 200) {
            city = (CityMapper.fromDto(cityDto));
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.current_city_widget);
            views.setTextViewText(R.id.tv_widget_temp, city.getmTemperature());
            views.setTextViewText(R.id.tv_widget_city, city.getmName());
            mAppWidgetManager.updateAppWidget(id, views);


            ArrayList<CityWidget> cityWidgets = Tools.loadPreferences(Constants.PREFS_WIDGETS_CITY, mContext, CityWidget.class);
            for (CityWidget cityWidget: cityWidgets) {
                if (cityWidget.getWidgetId() == id) {
                    cityWidget.setCity(city);
                    found = true;
                    break;
                }
            }
            if(!found) {
                cityWidgets.add(new CityWidget(id, city));
            }
//            cityWidgets.add(new CityWidget(appWidgetId, new City("Nevers", "Trop chaud", "35°C", 1)));
            Tools.savePreferences(Constants.PREFS_WIDGETS_CITY, mContext, cityWidgets);
        }
    }

    @Override
    public void onAPINoInternetAccess() {

    }
}