package com.example.grocerylist.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert (Recipe recipe);

    @Delete
    void delete (Recipe recipe);

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE recipe_name = :name LIMIT 1")
    LiveData<Recipe> getRecipeByName(String name);
}
