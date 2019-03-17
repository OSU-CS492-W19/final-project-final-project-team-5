package com.example.grocerylist.data.Recipe;

import android.os.AsyncTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.grocerylist.utils.RecipeUtils;
import com.example.grocerylist.utils.NetworkUtils;

public class LoadRecipeTask extends AsyncTask<Void, Void, String>{

    public interface AsyncCallback {
        void onRecipeLoadFinished(RecipeData recipe);
    }

    private String mURL;
    private AsyncCallback mCallback;

    LoadRecipeTask(String url, AsyncCallback callback) {
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
        RecipeData recipe = null;
        if (s != null) {
            RecipeUtils.RecipeResult result = RecipeUtils.parseRecipeJson(s);
            recipe = RecipeUtils.makeRecipeData(result);
        }
        mCallback.onRecipeLoadFinished(recipe);
    }
}
