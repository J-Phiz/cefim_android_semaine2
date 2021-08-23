package fr.jpsave.android.mymeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTvCityName;
    private TextView mTvCityDesc;
    private TextView mTvCityTemp;
    private ImageView mIvDescIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvCityName = findViewById(R.id.tv_city_name);
        mTvCityDesc = findViewById(R.id.tv_city_desc);
        mTvCityTemp = findViewById(R.id.tv_city_temp);
        mIvDescIcon = findViewById(R.id.iv_desc_icon);

        mTvCityName.setText(R.string.city_name);
        mTvCityDesc.setText(R.string.city_desc);
        mTvCityTemp.setText(R.string.city_temp);
        mIvDescIcon.setImageResource(R.drawable.weather_sunny_white);
    }
}