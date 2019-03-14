package com.example.grocerylist.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.grocerylist.utils.RecipieUtils;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "recipes")
public class Recipe implements Serializable {
    @NonNull
    @PrimaryKey
    public String recipe_name;
    //public RecipieUtils.Ingredient[] recipe_ingredients;
    //public String recipe_instructions;
    public String recipe_html_url;

}
