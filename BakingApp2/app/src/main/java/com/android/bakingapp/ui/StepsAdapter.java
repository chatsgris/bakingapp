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
 * Created by liumi on 7/8/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private JSONArray mJsonArray;
    private LayoutInflater mInflater;
    private String TAG = StepsAdapter.class.getSimpleName();

    StepsAdapter(Context context, JSONArray jsonArray) {
        this.mJsonArray = jsonArray;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String shortDescription = null;
        try {
            shortDescription = mJsonArray.getJSONObject(position).getString("shortDescription");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to retrieve Step Short Description from JSON Array");
        }
        holder.shortDescriptionTv.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView shortDescriptionTv;
        ViewHolder(View itemView) {
            super(itemView);
            shortDescriptionTv = itemView.findViewById(R.id.short_description);
        }
    }
}
