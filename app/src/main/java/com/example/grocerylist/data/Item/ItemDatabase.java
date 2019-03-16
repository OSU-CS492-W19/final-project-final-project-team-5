package com.example.grocerylist.data.Item;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ItemData.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    private static volatile ItemDatabase INSTANCE;

    static ItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, "item_db").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ItemDao ItemDao();
}