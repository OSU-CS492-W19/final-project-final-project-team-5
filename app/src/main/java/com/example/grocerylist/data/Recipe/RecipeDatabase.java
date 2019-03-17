package com.example.grocerylist.data.Recipe;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RecipeData.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    private static volatile RecipeDatabase INSTANCE;

    static RecipeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, "recipe_db").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RecipeDao RecipeDao();
}