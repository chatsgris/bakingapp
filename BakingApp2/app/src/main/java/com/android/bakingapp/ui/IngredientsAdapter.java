package com.android.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bakingapp.R;

import org.json.JSONArray;

/**
 * Created by liumi on 7/7/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private String TAG = IngredientsAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private JSONArray mJsonArray;
    private int mPosition;


    IngredientsAdapter(Context context, JSONArray jsonArray, int position) {
        this.mInflater = LayoutInflater.from(context);
        this.mJsonArray = jsonArray;
        this.mPosition = position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeItemView;

        ViewHolder(View itemView) {
            super(itemView);
            recipeItemView = itemView.findViewById(R.id.recipe_item);
        }
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
