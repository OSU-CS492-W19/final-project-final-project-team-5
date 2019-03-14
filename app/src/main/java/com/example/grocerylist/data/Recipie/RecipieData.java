package com.example.grocerylist.data.Recipie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "recipies")
public  class RecipieData implements  Serializable{
    @NonNull
    @PrimaryKey
    public String recipie_id;

    public String recipe_infox_json;
    public String recipie_result_json;
}



