package com.example.grocerylist.data.Recipe;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "recipes")
public  class RecipeData implements  Serializable{
    @NonNull
    @PrimaryKey
    public String recipe_id;

   // public String recipe_infox_json;
    public String recipe_result_json;
}



