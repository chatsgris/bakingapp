package com.android.bakingapp.data;

import android.net.Uri;
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
        JSONArray jsonArray = null;
        try {
            jsonArray = mRecipes.getJSONObject(position).getJSONArray("ingredients");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get ingredients from JSONArray");
        }
        return jsonArray;
    }

    public String getRecipeTitle(int position) {
        String string = null;
        try {
            string = mRecipes.getJSONObject(position).getString("name");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return string;
    }

    public String getImageUri(int position) {
        String uri = null;
        try {
            String string = mRecipes.getJSONObject(position).getString("image");
            if (string.length() != 0 && (string.endsWith(".jpg") || string.endsWith(".gif") || string.endsWith(".png"))) {
                uri = string;
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get image from JSONArray");
        }
        return uri;
    }

    public JSONArray getSteps(int position) {
        JSONArray jsonArray = null;
        try {
            jsonArray = mRecipes.getJSONObject(position).getJSONArray("steps");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get steps from JSONArray");
        }
        return jsonArray;
    }

    public Uri getMediaUri(int stepId, int position) {
        Uri uri = null;
        try {
            String string = getSteps(position).getJSONObject(stepId).getString("videoURL");
            if (string.length() != 0 && string.endsWith(".mp4")) {
                uri = Uri.parse(string);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get video from JSONArray");
        }
        return uri;
    }

    public String getStepDescription(int stepId, int position) {
        String string = null;
        try {
            string = getSteps(position).getJSONObject(stepId).getString("description");
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get Step Description from JSONArray");
        }
        return string;
    }

    public String getThumbnailUri(int stepId, int position) {
        String uri = null;
        try {
            String string = getSteps(position).getJSONObject(stepId).getString("thumbnailURL");
            if (string.length() != 0 && (string.endsWith(".jpg") || string.endsWith(".gif") || string.endsWith(".png"))) {
                uri = string;
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to get thumbnail from JSONArray");
        }
        return uri;
    }

    @Override
    public void processFinish(JSONArray output) {
    }
}
