package com.android.bakingapp.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.android.bakingapp.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class RecipeListQueryTask extends AsyncTask<URL, Void, JSONArray> {

    public interface RecipeListAsyncResponse {
        void processFinish(JSONArray output);
    }

    RecipeListAsyncResponse delegate = null;

    public RecipeListQueryTask(RecipeListAsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected JSONArray doInBackground(URL... urls) {
        URL url = urls[0];
        String response = null;
        try {
            response = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(Recipes.class.getSimpleName(), "Failed to get recipes response from URL");
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
        } catch (JSONException e) {
            Log.e(Recipes.class.getSimpleName(), "Failed to parse recipes response into JSONArray");
        }
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        delegate.processFinish(result);
    }
}
