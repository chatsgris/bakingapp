package com.android.bakingapp.data;

import android.util.Log;
import com.android.bakingapp.utils.NetworkUtils;
import com.android.bakingapp.utils.RecipeListQueryTask;
import org.json.JSONArray;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Recipes implements RecipeListQueryTask.RecipeListAsyncResponse{
    private final String TAG = Recipes.class.getSimpleName();

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

    @Override
    public void processFinish(JSONArray output) {
    }
}
