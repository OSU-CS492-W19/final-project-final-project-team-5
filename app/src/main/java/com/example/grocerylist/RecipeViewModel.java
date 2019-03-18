package com.example.grocerylist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.grocerylist.data.LoadRecipeTask;
import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.data.Recipe.RecipeDataRepository;
import com.example.grocerylist.data.recipeSearchResult;
import com.example.grocerylist.utils.RecipeUtils;

import java.util.List;

import static com.example.grocerylist.utils.RecipeUtils.buildRecipeURL;

public class RecipeViewModel extends AndroidViewModel implements LoadRecipeTask.AsyncCallback {
    private RecipeDataRepository mRecipeRepository;

    private MutableLiveData<RecipeUtils.RecipeInfo> mData;


    public RecipeViewModel(Application application){
        super(application);
        mData = new MutableLiveData<RecipeUtils.RecipeInfo>();
        mRecipeRepository = new RecipeDataRepository(application);
        RecipeUtils.RecipeInfo info = new RecipeUtils.RecipeInfo();
        mData.setValue(info);
    }

    public void insertRecipe(RecipeData recipe){
        mRecipeRepository.insertRecipeData(recipe);
    }

    public void deleteRecipe(RecipeData recipe){
        mRecipeRepository.deleteRecipeData(recipe);
    }

    public LiveData<List<RecipeData>> getAllRecipes(){
        return mRecipeRepository.getAllRecipes();
    }

    public LiveData<RecipeData> getRecipeByName(String name){
        return mRecipeRepository.getRecipeByName(name);
    }

    public void loadRecipe(RecipeUtils.RecipeInfox infox) {
        RecipeUtils.RecipeInfo recipeInfo = mData.getValue();
        recipeInfo.recipeInfox = infox;
        mData.setValue(recipeInfo);
        String Url = buildRecipeURL(infox.RecipeID);
        new LoadRecipeTask(Url, this).execute();

    }

    @Override
    public void onSearchRecipesLoadFinished(RecipeUtils.RecipeResult result) {
        RecipeUtils.RecipeInfo recipeInfo = mData.getValue();
        recipeInfo.recipeResult = result;
        mData.setValue(recipeInfo);
    }

    public LiveData<RecipeUtils.RecipeInfo> getRecipeInfo(){
        return mData;
    }

    public void setRecipeInfo(RecipeUtils.RecipeInfo info){
        mData.setValue(info);
    }
}
