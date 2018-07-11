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
 * Created by liumi on 7/6/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private JSONArray mJsonArray;
    private String TAG = RecipeListAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    RecipeListAdapter(Context context, JSONArray jsonArray) {
        this.mInflater = LayoutInflater.from(context);
        this.mJsonArray = jsonArray;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_item) TextView recipeItemView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.ViewHolder holder, final int position) {
        String text = null;
        try {
            text = mJsonArray.getJSONObject(position).getString("name");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse recipe name from JSONArray");
        }
        holder.recipeItemView.setText(text);
        holder.recipeItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }

    public void setOnClick(OnItemClicked onClick) {this.onClick=onClick;}
}
