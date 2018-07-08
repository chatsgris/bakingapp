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
 * Created by liumi on 7/6/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private JSONArray mJsonArray;
    private String TAG = RecipeListAdapter.class.getSimpleName();
    private LayoutInflater mInflater;

    RecipeListAdapter(Context context, JSONArray jsonArray) {
        this.mInflater = LayoutInflater.from(context);
        this.mJsonArray = jsonArray;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeItemView;

        ViewHolder(View itemView) {
            super(itemView);
            recipeItemView = itemView.findViewById(R.id.recipe_item);
        }
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.ViewHolder holder, int position) {
        String text = null;
        try {
            text = mJsonArray.getJSONObject(position).getString("name");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse recipe name from JSONArray");
        }
        holder.recipeItemView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }
}
