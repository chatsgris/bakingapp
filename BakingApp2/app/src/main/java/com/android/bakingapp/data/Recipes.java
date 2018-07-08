package com.android.bakingapp.data;

import android.util.Log;
import com.android.bakingapp.utils.NetworkUtils;
import com.android.bakingapp.utils.RecipeListQueryTask;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Recipes implements RecipeListQueryTask.RecipeListAsyncResponse{
    private final String TAG = Recipes.class.getSimpleName();
    private JSONArray mRecipes = getRecipes();

    public JSONArray getRecipes() {
        URL url = NetworkUtils.buildRecipeListUrl();
        JSONArray jsonArray = null;
        try {
            jsonArray = new RecipeListQueryTask(this).execute(url).get();
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray getIngredients(int position) {
        try {
            return mRecipes.getJSONObject(position).getJSONArray("ingredients");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get ingredients from JSONArray");
        }
    }

    @Override
    public void processFinish(JSONArray output) {
    }
}
