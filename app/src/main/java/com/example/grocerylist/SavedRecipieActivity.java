package com.example.grocerylist;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

import com.example.grocerylist.utils.RecipieUtils;

public class SavedRecipieActivity extends AppCompatActivity implements RecipieAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecipieistRV;
    private RecipieAdapter mRecipieAdapter;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipieistRV = findViewById(R.id.rv_grocery_list);//TODO: Update with corect

        mRecipieistRV.setLayoutManager(new LinearLayoutManager(this));
        mRecipieistRV.setHasFixedSize(true);

        mRecipieAdapter = new RecipieAdapter(this);
        mRecipieistRV.setAdapter(mRecipieAdapter);

        mRecipieistRV.setItemAnimator(new DefaultItemAnimator());

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
                ((RecipieAdapter.RecipieViewHolder) viewHolder).removeFromList();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecipieistRV);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.nav_saved_recipes:
                return true;
            case R.id.nav_grocery_list:
                return true;
        }
        return false;
    }

    @Override
    public void onItemClicked(RecipieUtils.RecipeInfo recipeInfo) {
     ////Open Resipy Detail Activity Via Intent
    }
}

