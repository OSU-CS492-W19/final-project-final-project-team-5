package com.example.grocerylist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.utils.RecipeUtils;

import java.util.List;

public class SavedRecipeActivity extends AppCompatActivity implements RecipeInfoAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecipeListRV;
    private RecipeInfoAdapter mRecipeAdapter;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_recipes_activity);

        mRecipeListRV = findViewById(R.id.rv_saved_recipes_list);//TODO: Update with corect
        mRecipeListRV.setLayoutManager(new LinearLayoutManager(this));
        mRecipeListRV.setHasFixedSize(true);

        mRecipeAdapter = new RecipeInfoAdapter(this);
        mRecipeListRV.setAdapter(mRecipeAdapter);

        mRecipeListRV.setItemAnimator(new DefaultItemAnimator());

        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        viewModel.getAllRecipes().observe(this, new Observer<List<RecipeData>>() {
            @Override
            public void onChanged(@Nullable List<RecipeData> recipeData) {
                //mRecipeAdapter.updateItems(recipeData); //TODO change adapter
            }
        });



        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((RecipeInfoAdapter.RecipeViewHolder) viewHolder).removeFromList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecipeListRV);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                Intent searchRecipesIntent = new Intent(this, recipeSearchActivity.class);
                startActivity(searchRecipesIntent);
                return true;
            case R.id.nav_saved_recipes:
                return true;
            case R.id.nav_grocery_list:
                Intent MainActivityIntent = new Intent(this, MainActivity.class);
                startActivity(MainActivityIntent);
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(RecipeUtils.RecipeInfo recipeInfo) {
        Intent RecipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
        RecipeDetailIntent.putExtra(RecipeUtils.EXTRA_RECIPE_INFO, recipeInfo);
        startActivity(RecipeDetailIntent);
    }
}

