package fr.jpsave.android.mymeteo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.adapter.FavoriteAdapter;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.tools.Tools;

public class FavoriteActivity extends AppCompatActivity {

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
                        City city = new City(
                                Tools.formatCityName(newCityName.getText().toString()),
                                "Ensoleillé",
                                "32°C",
                                R.drawable.weather_sunny_grey);
                        mCities.add(city);
                        mFavoriteAdapter.notifyDataSetChanged();
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

        City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.weather_rainy_grey);
        City city2 = new City("New York", "Ensoleillé", "22°C", R.drawable.weather_sunny_grey);
        City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.weather_foggy_grey);
        City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_rainy_grey);
        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);


    }
}