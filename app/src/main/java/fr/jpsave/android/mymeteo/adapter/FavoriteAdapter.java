package fr.jpsave.android.mymeteo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import fr.jpsave.android.mymeteo.R;
import fr.jpsave.android.mymeteo.model.City;
import fr.jpsave.android.mymeteo.tools.Tools;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private final FavoriteAdapter mAdapter;
    private ArrayList<City> mCities;

    public FavoriteAdapter(Context context, ArrayList<City> cities) {
        mContext = context;
        mCities = cities;
        mAdapter = this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
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
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(mContext, "Suppression de la ville "+city.getmName(), Toast.LENGTH_SHORT).show();

            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    new ContextThemeWrapper(mContext, R.style.Theme_MyMeteo_AlertDialog)
            );
            builder.setMessage(mContext.getString(R.string.suppress_favorite_message) + " " + city.getmName());
            builder.setNegativeButton(R.string.add_favorite_button_cancel, null);
            builder.setPositiveButton(R.string.add_favorite_button_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    for (City c : mCities) {
                        if (city.getmName().equals(c.getmName())) {
                            mCities.remove(c);
                            break;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            builder.create().show();

            return false;
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
        holder.ivIcon.setImageResource(city.getmWeatherIcon());
        holder.city = city;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
