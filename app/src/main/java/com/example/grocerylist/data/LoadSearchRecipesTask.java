package com.example.grocerylist.data;

import android.os.AsyncTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.grocerylist.utils.RecipeUtils;
import com.example.grocerylist.utils.NetworkUtils;


class LoadSearchRecipesTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onSearchRecipesLoadFinished(List<recipeSearchResult> RecipeSearchResults);
    }

    private String mURL;
    private AsyncCallback mCallback;

    LoadSearchRecipesTask(String url, AsyncCallback callback) {
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
        ArrayList<recipeSearchResult> RecipeSearchResults = null;
        if (s != null) {
            RecipeSearchResults = RecipeUtils.parseRecipeSearchJson(s);
        }
        mCallback.onSearchRecipesLoadFinished(RecipeSearchResults);
    }
}

