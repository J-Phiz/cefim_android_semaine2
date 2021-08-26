package fr.jpsave.android.mymeteo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.activity.FavoriteActivity;
import fr.jpsave.android.mymeteo.activity.MapsActivity;
import fr.jpsave.android.mymeteo.constants.Constants;
import fr.jpsave.android.mymeteo.constants.ConvertWeatherIcons;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.tools.Tools;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<City> mCities;

    public FavoriteAdapter(Context context, ArrayList<City> cities) {
        mContext = context;
        mCities = cities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCity;
        public TextView tvDesc;
        public TextView tvTemp;
        public ImageView ivIcon;
        public City city;

        public ViewHolder(View view) {
            super(view);
            tvCity = view.findViewById(R.id.favorite_item_city);
            tvDesc = view.findViewById(R.id.favorite_item_desc);
            tvTemp = view.findViewById(R.id.favorite_item_temp);
            ivIcon = view.findViewById(R.id.favorite_item_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MapsActivity.class);
            intent.putExtra(Constants.MSG_MAPS_ACT, new Gson().toJson(city));
            view.getContext().startActivity(intent);
        }
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        City city = mCities.get(position);
        holder.tvCity.setText(city.getmName());
        holder.tvDesc.setText(city.getmDescription());
        holder.tvTemp.setText(city.getmTemperature());
        holder.ivIcon.setImageResource(
                ConvertWeatherIcons.getIcon(city.getmWeatherIcon(), ConvertWeatherIcons.IconColor.GREY)
        );
        holder.city = city;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
