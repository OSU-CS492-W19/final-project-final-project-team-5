package com.example.grocerylist.data.Recipe;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeData recipeData);

    @Delete
    void delete(RecipeData recipeData);

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeData>> getRecipes();

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipe LIMIT 1")
    LiveData<RecipeData> getRecipe(String recipe);
}