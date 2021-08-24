package fr.jpsave.android.mymeteo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Locale;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.client.ClientAPI;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.constants.ConvertWeatherIcons;
import fr.jpsave.android.mymeteo.dto.CityDTO;
import fr.jpsave.android.mymeteo.mapper.CityMapper;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.tools.Tools;

public class MainActivity extends AppCompatActivity implements ClientAPI {

    private TextView mTvCityName;
    private TextView mTvCityDesc;
    private TextView mTvCityTemp;
    private ImageView mIvDescIcon;
    private TextView mTvError;
    private LinearLayout mLlTempInfos;
    private FloatingActionButton mFabFavorite;
    private EditText mEtTextToTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvCityName = findViewById(R.id.tv_city_name);
        mTvCityDesc = findViewById(R.id.tv_city_desc);
        mTvCityTemp = findViewById(R.id.tv_city_temp);
        mIvDescIcon = findViewById(R.id.iv_desc_icon);
        mTvError = findViewById(R.id.tv_error);
        mLlTempInfos = findViewById(R.id.ll_temp_infos);
        mFabFavorite = findViewById(R.id.favoriteButton);
        mEtTextToTransfer = findViewById(R.id.et_text_to_transfer);

        if (!Tools.checkInternetConnection(this)) {
            mTvError.setText(R.string.no_internet);
            mTvError.setVisibility(View.VISIBLE);
            mLlTempInfos.setVisibility(View.INVISIBLE);
            mFabFavorite.setVisibility(View.INVISIBLE);
        } else {
            mTvError.setVisibility(View.INVISIBLE);
            mLlTempInfos.setVisibility(View.VISIBLE);
            mFabFavorite.setVisibility(View.VISIBLE);

            callAPI(
                    this,
                    Constants.WEATHER_API_URL_WITH_COMMON_OPTS + "&q=" + "Tours,fr"
            );
        }
    }

    public void defaultOnClickFavoriteButton(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(Constants.MSG_MAIN_ACT, mEtTextToTransfer.getText() != null ? mEtTextToTransfer.getText().toString() : "");
        startActivity(intent);
    }


    private void renderCurrentWeather(City city) {
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