package com.example.grocerylist.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;
import android.app.Application;
import android.os.AsyncTask;
import java.util.List;

import com.example.grocerylist.utils.RecipeUtils;

public class recipeSearchResultRepository implements LoadSearchRecipesTask.AsyncCallback{

    private static final String TAG = recipeSearchResultRepository.class.getSimpleName();

    private MutableLiveData<List<recipeSearchResult>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mkw_title;
    private int mPage;
    private int mRecipes_per_page;

    public recipeSearchResultRepository(){
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mkw_title = null;
        mPage = -1;
        mRecipes_per_page = -1;
    }

    public void loadRecipeSearch(String kw_title, int page, int recipes_per_page) {
        if (shouldSearchRecipes(kw_title, page, recipes_per_page)) {
            mkw_title = kw_title;
            mPage = page;
            mRecipes_per_page = recipes_per_page;
            mSearchResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = RecipeUtils.buildRecipeSearchURL(kw_title, page, recipes_per_page);
            Log.d(TAG, "fetching new forecast data with this URL: " + url);
            new LoadSearchRecipesTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached forecast data");
        }
    }

    public LiveData<List<recipeSearchResult>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    private boolean shouldSearchRecipes(String kw_title, int page, int recipes_per_page) {
        if (!TextUtils.equals(kw_title, mkw_title) || !(page == mPage) || !(recipes_per_page == mRecipes_per_page)) {
            return true;
        } else {
            List<recipeSearchResult> searchResults = mSearchResults.getValue();
            if (searchResults == null || searchResults.size() == 0) {
                return true;
            } else {
            //    return searchResults.get(0).dateTime.before(new Date());
                return false;
            }
        }
    }

    @Override
    public void onSearchRecipesLoadFinished(List<recipeSearchResult> searchResults) {
        mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
