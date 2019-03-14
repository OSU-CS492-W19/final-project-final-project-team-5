package com.example.grocerylist.data.Item;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ItemData itemData);

    @Delete
    void delete(ItemData itemData);

    @Query("SELECT * FROM items")
    LiveData<List<ItemData>> getItems();

    @Query("SELECT * FROM items WHERE item = :i_tem LIMIT 1")
    LiveData<ItemData> getItem(String i_tem);
}