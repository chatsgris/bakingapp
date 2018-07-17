package com.android.bakingapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;


public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mPosition;
    private JSONArray mJSONArray;
    String TAG = RecipeWidgetService.class.getSimpleName();

    public RecipeViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        mPosition = intent.getIntExtra("Position", -1);
        mJSONArray = new Recipes().getIngredients(mPosition);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mJSONArray.length();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        try {
            views.setTextViewText(R.id.ingredient_item_widget, mJSONArray.getJSONObject(position).getString("ingredient"));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get ingredient from JSONArray");
        }


        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        views.setOnClickFillInIntent(R.id.ingredient_item_widget, intent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
