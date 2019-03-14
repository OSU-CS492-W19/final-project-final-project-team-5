package com.example.grocerylist.data.Item;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "items")
public  class ItemData implements  Serializable{
    @NonNull
    @PrimaryKey
    public String item;
}
