package com.android.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by liumi on 7/7/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private String TAG = IngredientsAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private JSONArray mJsonArray;


    IngredientsAdapter(Context context, JSONArray jsonArray) {
        this.mInflater = LayoutInflater.from(context);
        this.mJsonArray = jsonArray;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantityTitleTv;
        TextView quantityValueTv;
        TextView measureTitleTv;
        TextView measureValueTv;
        TextView ingredientTitleTv;
        TextView ingredientValueTv;

        ViewHolder(View itemView) {
            super(itemView);
            quantityTitleTv = itemView.findViewById(R.id.quantity_title);
            quantityValueTv = itemView.findViewById(R.id.quantity_value);
            measureTitleTv = itemView.findViewById(R.id.measure_title);
            measureValueTv = itemView.findViewById(R.id.measure_value);
            ingredientTitleTv = itemView.findViewById(R.id.ingredient_title);
            ingredientValueTv = itemView.findViewById(R.id.ingredient_value);
        }
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {
        String quantityValue = null;
        String measureValue = null;
        String ingredientValue = null;
        try {
            quantityValue = mJsonArray.getJSONObject(position).getString("quantity");
            measureValue = mJsonArray.getJSONObject(position).getString("measure");
            ingredientValue = mJsonArray.getJSONObject(position).getString("ingredient");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to retrieve ingredient information from JSONObject");
        }
        holder.ingredientValueTv.setText(ingredientValue);
        holder.measureValueTv.setText(measureValue);
        holder.quantityValueTv.setText(quantityValue);
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }
}
