package com.example.grocerylist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GroceryAdapter.OnItemCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mGroceryListRV;
    private EditText mGroceryEntryET;
    private GroceryAdapter mGroceryAdapter;
    private Toast mToast;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroceryListRV = findViewById(R.id.rv_grocery_list);
        mGroceryEntryET = findViewById(R.id.et_item_entry);

        mGroceryListRV.setLayoutManager(new LinearLayoutManager(this));
        mGroceryListRV.setHasFixedSize(true);

        mGroceryAdapter = new GroceryAdapter(this);
        mGroceryListRV.setAdapter(mGroceryAdapter);

        mGroceryListRV.setItemAnimator(new DefaultItemAnimator());

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mToast = null;

        Button addTodoButton = findViewById(R.id.btn_add_item);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoText = mGroceryEntryET.getText().toString();
                if (!TextUtils.isEmpty(todoText)) {
                    mGroceryListRV.scrollToPosition(0);
                    mGroceryAdapter.addItem(todoText);
                    mGroceryEntryET.setText("");
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((GroceryAdapter.GroceryViewHolder)viewHolder).removeFromList();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGroceryListRV);
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
    public void onItemCheckedChanged(String item, boolean b){
        if (mToast != null){
            mToast.cancel();
        }
        String completedState = b ? "PURCHASED" : "MARKED NOT PURCHASED";
        String toastText = completedState + ": " + item;
        mToast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        mToast.show();
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
                Intent savedRecipesIntent = new Intent(this, SavedRecipesActivity.class);
                startActivity(savedRecipesIntent);
                return true;
            case R.id.nav_grocery_list:
                return true;
        }
        return false;
    }
}
