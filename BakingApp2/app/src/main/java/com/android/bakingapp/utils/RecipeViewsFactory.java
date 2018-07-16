package com.android.bakingapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by mimiliu on 7/16/18.
 */

public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int appWidgetId;
    private int mPosition;
    private JSONArray mJSONArray;
    String TAG = RecipeWidgetService.class.getSimpleName();

    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer",
            "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae",
            "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat",
            "placerat", "ante",
            "porttitor", "sodales",
            "pellentesque", "augue",
            "purus"};

    public RecipeViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
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
        /**try {
            views.setTextViewText(R.id.ingredient_item_widget, items[position]);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get ingredient from JSONArray");
        }**/
        views.setTextViewText(R.id.ingredient_item_widget, items[position]);

        Bundle bundle = new Bundle();
        bundle.putInt("Position", mPosition);
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
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return mPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
