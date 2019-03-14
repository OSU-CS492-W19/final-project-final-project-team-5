package com.example.grocerylist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.grocerylist.data.Recipe;
import com.example.grocerylist.utils.RecipieUtils;

import java.util.List;

public class SavedRecipesActivity extends AppCompatActivity implements RecipeSearchAdapter.OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes_activity);

        RecyclerView savedRecipesRV = findViewById(R.id.rv_saved_recipes_list);
        savedRecipesRV.setLayoutManager(new LinearLayoutManager(this));
        savedRecipesRV.setHasFixedSize(true);

        final RecipeSearchAdapter adapter = new RecipeSearchAdapter(this);
        savedRecipesRV.setAdapter(adapter);

        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>(){

            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                adapter.updateSearchResults(recipes);
            }
        });
    }

    @Override
    public void onSearchItemClick(Recipe recipe){
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        startActivity(intent);
    }
}
