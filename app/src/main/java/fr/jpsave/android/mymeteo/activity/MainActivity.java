package fr.jpsave.android.mymeteo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.client.ClientAPI;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.constants.ConvertWeatherIcons;
import fr.jpsave.android.mymeteo.dto.CityDTO;
import fr.jpsave.android.mymeteo.mapper.CityMapper;
import fr.jpsave.android.mymeteo.model.City;

public class MainActivity extends AppCompatActivity implements ClientAPI {

    private static final int REQUEST_CODE = 12;

    private Activity mContext;
    private TextView mTvCityName;
    private TextView mTvCityDesc;
    private TextView mTvCityTemp;
    private ImageView mIvDescIcon;
    private TextView mTvError;
    private LinearLayout mLlTempInfos;
    private FloatingActionButton mFabFavorite;
    private EditText mEtTextToTransfer;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mTvCityName = findViewById(R.id.tv_city_name);
        mTvCityDesc = findViewById(R.id.tv_city_desc);
        mTvCityTemp = findViewById(R.id.tv_city_temp);
        mIvDescIcon = findViewById(R.id.iv_desc_icon);
        mTvError = findViewById(R.id.tv_error);
        mLlTempInfos = findViewById(R.id.ll_temp_infos);
        mFabFavorite = findViewById(R.id.favoriteButton);
        mEtTextToTransfer = findViewById(R.id.et_text_to_transfer);

        // Visibility in error mode by default
        mTvError.setVisibility(View.VISIBLE);
        mLlTempInfos.setVisibility(View.INVISIBLE);
        mFabFavorite.setVisibility(View.INVISIBLE);

        // Init position and call api to set weather infos when it's done
        initPosition();
    }

    private void initPosition() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("MyMeteoLoc", "Location {lat=" + location.getLatitude() + " ; lon=" + location.getLongitude() + "}");

                callAPI(
                        mContext,
                        Constants.WEATHER_API_URL_WITH_COMMON_OPTS + "&lat=" + location.getLatitude() +
                                "&lon=" + location.getLongitude()
                );

                mLocationManager.removeUpdates(mLocationListener);
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                failure(R.string.no_position);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
        mLocationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initPosition();
                } else {
                    failure(R.string.no_position);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults); }
    }


    public void defaultOnClickFavoriteButton(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(Constants.MSG_MAIN_ACT, mEtTextToTransfer.getText() != null ? mEtTextToTransfer.getText().toString() : "");
        startActivity(intent);
    }


    private void renderCurrentWeather(City city) {
        mTvError.setVisibility(View.INVISIBLE);
        mLlTempInfos.setVisibility(View.VISIBLE);
        mFabFavorite.setVisibility(View.VISIBLE);
        mTvCityName.setText(city.getmName());
        mTvCityDesc.setText(city.getmDescription());
        mTvCityTemp.setText(city.getmTemperature());
        mIvDescIcon.setImageResource(
                ConvertWeatherIcons.getIcon(city.getmWeatherIcon(), ConvertWeatherIcons.IconColor.WHITE)
        );
    }

    private void failure(int msgId) {
        mTvError.setText(msgId);
        mTvError.setVisibility(View.VISIBLE);
        mLlTempInfos.setVisibility(View.INVISIBLE);
        mFabFavorite.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAPIFailure() {
        failure(R.string.no_result_db_access);
    }

    @Override
    public void onAPISuccess(String json) {
        Gson gson = new Gson();
        CityDTO cityDto = gson.fromJson(json, CityDTO.class);
        if (cityDto != null && cityDto.cod == 200) {
            renderCurrentWeather(CityMapper.fromDto(cityDto));
        } else {
            failure(R.string.no_result_db_access);
        }
    }

    @Override
    public void onAPINoInternetAccess() {
        failure(R.string.no_internet);
    }
}