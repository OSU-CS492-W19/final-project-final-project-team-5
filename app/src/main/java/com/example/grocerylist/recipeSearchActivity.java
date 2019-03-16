package com.example.grocerylist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.grocerylist.utils.RecipeUtils;
import com.example.grocerylist.data.recipeSearchResult;
import com.example.grocerylist.data.Status;

import java.io.Serializable;
import java.util.List;

public class recipeSearchActivity extends AppCompatActivity implements recipeSearchResultsAdapter.OnResultItemClickListener{

    private RecyclerView mRecipesListRV;
    private ProgressBar mProgressBarPB;
    private recipeSearchResultsViewModel mrecipeSearchResultsViewModel;
    private recipeSearchResultsAdapter mAdapter;
    private TextView mLoadingErrorMessageTV;
    private EditText mSearchEntry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_search_activity);

        mRecipesListRV = findViewById(R.id.rv_recipe_search_items);
        mProgressBarPB = findViewById(R.id.pb_loading_indicator_search);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mSearchEntry = findViewById(R.id.et_search_entry);

        mAdapter = new recipeSearchResultsAdapter(this);
        mRecipesListRV.setAdapter(mAdapter);
        mRecipesListRV.setLayoutManager(new LinearLayoutManager(this));
        mRecipesListRV.setHasFixedSize(true);

        mrecipeSearchResultsViewModel = ViewModelProviders.of(this).get(recipeSearchResultsViewModel.class);

        mrecipeSearchResultsViewModel.getResults().observe(this, new Observer<List<recipeSearchResult>>() {
            @Override
            public void onChanged(@Nullable List<recipeSearchResult> recipeSearchResults) {
                mAdapter.updateResults(recipeSearchResults);
            }
        });

        mrecipeSearchResultsViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mProgressBarPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mProgressBarPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mRecipesListRV.setVisibility(View.VISIBLE);
                } else {
                    mProgressBarPB.setVisibility(View.INVISIBLE);
                    mRecipesListRV.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });

        Button addTodoButton = findViewById(R.id.btn_do_search);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mSearchEntry.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mRecipesListRV.scrollToPosition(0);
                 //   mGroceryAdapter.addItem(text);
                    doRecipeSearch(text);
                    mSearchEntry.setText("");
                }
            }
        });
    }

    public void doRecipeSearch(String kw_title){
        int page = 0;
        int recipes_per_page = 10;

        mrecipeSearchResultsViewModel.loadResults(kw_title, page, recipes_per_page);
    }

    @Override
    public void onResultItemClick(recipeSearchResult result) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        //intent.putExtra(RecipeUtils.EXTRA_RECIPE, (Serializable) result);
        startActivity(intent);

    }
}
