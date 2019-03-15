package com.example.grocerylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grocerylist.data.Recipe.RecipeData;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView mRecipeNameTV;
    private TextView mRecipeInstructionsTV;
    private ListView mRecipeIngredients;

    private RecipeData mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipeNameTV = findViewById(R.id.tv_recipe_name);
        mRecipeIngredients = findViewById(R.id.lv_ingredients);
        mRecipeInstructionsTV = findViewById(R.id.tv_instructions);

        // initialize ViewModel here

        mRecipe = null;



    }
}
