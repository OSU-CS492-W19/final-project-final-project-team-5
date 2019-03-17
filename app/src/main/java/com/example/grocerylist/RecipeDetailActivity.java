package com.example.grocerylist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.data.recipeSearchResult;
import com.example.grocerylist.utils.RecipeUtils;

import java.io.IOException;

import static com.example.grocerylist.utils.NetworkUtils.doHTTPGet;
import static com.example.grocerylist.utils.RecipeUtils.buildRecipeURL;
import static com.example.grocerylist.utils.RecipeUtils.parseRecipeJson;

public class RecipeDetailActivity extends AppCompatActivity{

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private TextView mRecipeNameTV;
    private TextView mRecipeCuisine;
    private TextView mRecipeSubcategory;
    private TextView mRecipeMicrocategory;
    private TextView mRecipeServings;
    private TextView mRecipeReviewCount;
    private TextView mRecipeStarRating;
    private TextView mRecipeInstructionsTV;
    private ListView mRecipeIngredients;

    private ImageView mAddIngredientsToListIV;
    private ImageView mSaveRecipeIV;

    private RecipeData mRecipeData;
    private RecipeViewModel mRecipeViewModel;
    private recipeSearchResult mRecipeSearchResult;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mAddIngredientsToListIV = findViewById(R.id.iv_add_items_to_list);
        mSaveRecipeIV = findViewById(R.id.iv_save_recipe);

        mRecipeNameTV = findViewById(R.id.tv_recipe_title);


        mRecipeNameTV = findViewById(R.id.tv_recipe_name);
        mRecipeIngredients = findViewById(R.id.lv_ingredients);
        mRecipeInstructionsTV = findViewById(R.id.tv_instructions);



        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        mRecipeSearchResult = null;
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(RecipeUtils.EXTRA_RECIPE_SEARCH_RESULT)){
            Log.d(TAG, "Intent is not null.");
            mRecipeSearchResult = (recipeSearchResult) intent.getSerializableExtra(RecipeUtils.EXTRA_RECIPE_SEARCH_RESULT);
        //    String recipeURL = buildRecipeURL(mRecipeSearchResult.RecipeID);
            Log.d(TAG, "Recipe Title: " + mRecipeSearchResult.Title);
            mRecipeNameTV.setText(mRecipeSearchResult.Title);

            //TODO: parse RecipeData, RecipeInfo, RecipeInfox, Ingredients, etc.

            mRecipeViewModel.getRecipeByName(mRecipeSearchResult.RecipeID).observe(this, new Observer<RecipeData>() {
                @Override
                public void onChanged(@Nullable RecipeData recipeData) {
                    if(recipeData != null){
                        mIsSaved = true;
                        mRecipeData = (mRecipeViewModel.getRecipeByName(mRecipeSearchResult.RecipeID)).getValue();
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_transparent);
                    } else {
                        mIsSaved = false;
                        mRecipeViewModel.loadRecipe(mRecipeSearchResult.RecipeID);
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_black_24dp);
                    }
                }
            });
        }


        mSaveRecipeIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mRecipeSearchResult != null){
                    if(!mIsSaved){
                        //mRecipeViewModel.insertRecipe(mRecipeData);
                    } else {
                        //mRecipeViewModel.deleteRecipe(mRecipeData);
                    }
                }
            }
        });

        mAddIngredientsToListIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Add the ingredients listed in the recipe to the grocery list
            }
        });
    }
}