package com.example.grocerylist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.data.Recipe.RecipeDataRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeDataRepository mRecipeRepository;

    public RecipeViewModel(Application application){
        super(application);
        mRecipeRepository = new RecipeDataRepository(application);
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

    public void loadRecipe(String id){
        mRecipeRepository.loadRecipeSearch(id);
    }
}
