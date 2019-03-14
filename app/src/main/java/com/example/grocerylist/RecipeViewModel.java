package com.example.grocerylist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.grocerylist.data.Recipe;
import com.example.grocerylist.data.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository mRecipeRepository;

    public RecipeViewModel(Application application){
        super(application);
        mRecipeRepository = new RecipeRepository(application);
    }

    public void insertRecipe(Recipe recipe){
        mRecipeRepository.insertRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        mRecipeRepository.deleteRecipe(recipe);
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return mRecipeRepository.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeByName(String name){
        return mRecipeRepository.getRecipeByName(name);
    }
}
