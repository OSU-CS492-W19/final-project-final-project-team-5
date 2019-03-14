package com.example.grocerylist.data.Recipie;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipieData recipieData);

    @Delete
    void delete(RecipieData recipieData);

    @Query("SELECT * FROM recipies")
    LiveData<List<RecipieData>> getRecipies();

    @Query("SELECT * FROM recipies WHERE recipie_id = :recipie LIMIT 1")
    LiveData<RecipieData> getRecipie(String recipie);
}