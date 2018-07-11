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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        @BindView(R.id.quantity_title) TextView quantityTitleTv;
        @BindView(R.id.quantity_value) TextView quantityValueTv;
        @BindView(R.id.measure_title) TextView measureTitleTv;
        @BindView(R.id.measure_value) TextView measureValueTv;
        @BindView(R.id.ingredient_title) TextView ingredientTitleTv;
        @BindView(R.id.ingredient_value) TextView ingredientValueTv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
