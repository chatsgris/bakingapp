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
 * Created by liumi on 7/8/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private JSONArray mJsonArray;
    private LayoutInflater mInflater;
    private String TAG = StepsAdapter.class.getSimpleName();
    private OnStepClicked onClick;

    public interface OnStepClicked {
        void onStepClick(int stepId);
    }

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
        int index = -1;

        try {
            shortDescription = mJsonArray.getJSONObject(position).getString("shortDescription");
            index = mJsonArray.getJSONObject(position).getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to retrieve Step Short Description from JSON Array");
        }
        holder.shortDescriptionTv.setText(shortDescription);

        final int stepId = index;
        holder.shortDescriptionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onStepClick(stepId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.short_description) TextView shortDescriptionTv;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnClick(OnStepClicked onClick) {this.onClick=onClick;}
}
