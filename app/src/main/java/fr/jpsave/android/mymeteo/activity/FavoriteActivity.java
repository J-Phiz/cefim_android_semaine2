package fr.jpsave.android.mymeteo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.adapter.FavoriteAdapter;
import fr.jpsave.android.mymeteo.client.ClientAPI;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.dto.CityDTO;
import fr.jpsave.android.mymeteo.mapper.CityMapper;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.tools.Tools;

public class FavoriteActivity extends AppCompatActivity implements ClientAPI {

    private Activity mContext;
    private ArrayList<City> mCities;
    private RecyclerView mRvFavorite;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                mContext,
                                Constants.WEATHER_API_URL_WITH_COMMON_OPTS +
                                        "&q=" + newCityName.getText().toString()
                        );
                    }
                });

                builder.create().show();
            }
        });

        Bundle extras = getIntent().getExtras();
        mContext = this;
        mCities = new ArrayList<>();
        mRvFavorite = findViewById(R.id.favorite_recycler_view);
        mRvFavorite.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteAdapter = new FavoriteAdapter(this, mCities);
        mRvFavorite.setAdapter(mFavoriteAdapter);
    }

    private void failure(int msgId) {
        Toast.makeText(mContext, msgId, Toast.LENGTH_LONG).show();
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
            mCities.add(CityMapper.fromDto(cityDto));
            mFavoriteAdapter.notifyDataSetChanged();
        } else {
            failure(R.string.no_result_db_access);
        }
    }

    @Override
    public void onAPINoInternetAccess() {
        failure(R.string.no_internet);
    }
}