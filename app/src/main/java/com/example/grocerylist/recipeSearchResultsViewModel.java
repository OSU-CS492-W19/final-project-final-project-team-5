package com.example.grocerylist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;

import com.example.grocerylist.data.recipeSearchResult;
import com.example.grocerylist.data.recipeSearchResultRespository;
import com.example.grocerylist.data.Status;

import java.util.List;

public class recipeSearchResultsViewModel extends ViewModel {

    private LiveData<List<recipeSearchResult>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    private recipeSearchResultRespository mRepository;

    public recipeSearchResultsViewModel() {
        mRepository = new recipeSearchResultRespository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadResults(String title_kw, int page, int recipes_per_page) {
        mRepository.loadRecipeSearch(title_kw, page, recipes_per_page);
    }

    public LiveData<List<recipeSearchResult>> getResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
