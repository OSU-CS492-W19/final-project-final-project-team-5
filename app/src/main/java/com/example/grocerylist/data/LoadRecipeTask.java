package com.example.grocerylist.data;

import android.os.AsyncTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.grocerylist.utils.RecipeUtils;
import com.example.grocerylist.utils.NetworkUtils;


public class LoadRecipeTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onSearchRecipesLoadFinished(RecipeUtils.RecipeResult result);
    }

    private String mURL;
    private AsyncCallback mCallback;

    public LoadRecipeTask(String url, AsyncCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String forecastJSON = null;
        try {
            forecastJSON = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forecastJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        RecipeUtils.RecipeResult RecipeSearchResult = null;
        RecipeUtils.RecipeResult result = null;
        if (s != null) {
            result = RecipeUtils.parseRecipeJson(s);
        }
        mCallback.onSearchRecipesLoadFinished(result);
    }
}

