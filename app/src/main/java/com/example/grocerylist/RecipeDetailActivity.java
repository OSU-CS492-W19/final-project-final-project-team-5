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

import com.example.grocerylist.data.Item.ItemData;
import com.example.grocerylist.data.LoadRecipeTask;
import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.data.recipeSearchResult;
import com.example.grocerylist.utils.RecipeUtils;

import java.io.IOException;
import java.util.List;

import static com.example.grocerylist.utils.NetworkUtils.doHTTPGet;
import static com.example.grocerylist.utils.RecipeUtils.buildRecipeURL;
import static com.example.grocerylist.utils.RecipeUtils.makeRecipieData;
import static com.example.grocerylist.utils.RecipeUtils.parseRecipeJson;

public class RecipeDetailActivity extends AppCompatActivity{

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private TextView mRecipeNameTV;
    private TextView mRecipeInstructionsTV;
    private TextView mRecipeIngredients;

    private Toast mToast;

    private ImageView mAddIngredientsToListIV;
    private ImageView mSaveRecipeIV;

    private ItemViewModel mItemViewModel;
    private RecipeViewModel mRecipeViewModel;
    private recipeSearchResult mRecipeSearchResult;
    private RecipeUtils.RecipeInfo mRecipeInfo;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mAddIngredientsToListIV = findViewById(R.id.iv_add_items_to_list);
        mSaveRecipeIV = findViewById(R.id.iv_save_recipe);

        mRecipeNameTV = findViewById(R.id.tv_recipe_title);


        mRecipeNameTV = findViewById(R.id.tv_recipe_name);
        mRecipeIngredients = findViewById(R.id.tv_ingredients);
        mRecipeInstructionsTV = findViewById(R.id.tv_instructions);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        mToast = null;

        mRecipeViewModel.getRecipeInfo().observe(this, new Observer<RecipeUtils.RecipeInfo>() {
            @Override
            public void onChanged(@Nullable RecipeUtils.RecipeInfo recipeInfo) {
                if(recipeInfo.recipeResult != null && recipeInfo.recipeInfox != null){
                    mRecipeNameTV.setText(recipeInfo.recipeInfox.Title);
                    mRecipeInstructionsTV.setText("Instructions: \n" + recipeInfo.recipeResult.Instructions);
                    String ingredients = "Ingredients: \n";
                    for(RecipeUtils.Ingredient ingr : recipeInfo.recipeResult.Ingredients){
                        ingredients += ("- " + ingr.Name + "\n");
                    }
                    mRecipeIngredients.setText(ingredients);
                }

            }
        });

        mRecipeInfo = null;
        mRecipeSearchResult = null;
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(RecipeUtils.EXTRA_RECIPE_SEARCH_RESULT)){ //if the user clicks on an item from the recipeSearchActivity

            mRecipeSearchResult = (recipeSearchResult) intent.getSerializableExtra(RecipeUtils.EXTRA_RECIPE_SEARCH_RESULT);

            Log.d(TAG, "Recipe Title: " + mRecipeSearchResult.Title);
            Log.d(TAG, "RecipeID: " + mRecipeSearchResult.RecipeID);

            RecipeUtils.RecipeInfox recipeInfox = new RecipeUtils.RecipeInfox();
            recipeInfox.Title = mRecipeSearchResult.Title;
            recipeInfox.Category = mRecipeSearchResult.Category;
            recipeInfox.Cuisine = mRecipeSearchResult.Cuisine;
            recipeInfox.Microcategory = mRecipeSearchResult.Microcategory;
            recipeInfox.RecipeID = mRecipeSearchResult.RecipeID;
            recipeInfox.ReviewCount = mRecipeSearchResult.ReviewCount;
            recipeInfox.Servings = mRecipeSearchResult.Servings;
            recipeInfox.StarRating = mRecipeSearchResult.StarRating;
            recipeInfox.Subcategory = mRecipeSearchResult.Subcategory;

            mRecipeViewModel.getRecipeByName(mRecipeSearchResult.RecipeID).observe(this, new Observer<RecipeData>() {
                @Override
                public void onChanged(@Nullable RecipeData recipeData) {
                    if(recipeData != null){
                        mIsSaved = true;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_transparent);
                    } else {
                        mIsSaved = false;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_black_24dp);
                    }
                }
            });

            if( mRecipeViewModel.getRecipeByName(recipeInfox.RecipeID).getValue() == null){
                mRecipeViewModel.loadRecipe(recipeInfox);
            }
            else{
                RecipeData data = mRecipeViewModel.getRecipeByName(recipeInfox.RecipeID).getValue();
                RecipeUtils.RecipeInfo recipeInfo = RecipeUtils.recipeDataToInfo(data);
                mRecipeViewModel.setRecipeInfo(recipeInfo);
            }
        }
        else if(intent != null && intent.hasExtra(RecipeUtils.EXTRA_RECIPE_INFO)){ //if the user clicks on an item from the SavedRecipeActivity

            mRecipeInfo = (RecipeUtils.RecipeInfo) intent.getSerializableExtra(RecipeUtils.EXTRA_RECIPE_INFO);

            Log.d(TAG, "Recipe Title: " + mRecipeInfo.recipeInfox.Title);
            Log.d(TAG, "RecipeID: " + mRecipeInfo.recipeInfox.RecipeID);

            mRecipeViewModel.getRecipeByName(mRecipeInfo.recipeInfox.RecipeID).observe(this, new Observer<RecipeData>() {
                @Override
                public void onChanged(@Nullable RecipeData recipeData) {
                    if(recipeData != null){
                        mIsSaved = true;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_transparent);
                    } else {
                        mIsSaved = false;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_black_24dp);
                    }
                }
            });

            if( mRecipeViewModel.getRecipeByName(mRecipeInfo.recipeInfox.RecipeID).getValue() == null){
                mRecipeViewModel.loadRecipe(mRecipeInfo.recipeInfox);
            }
            else{
                RecipeData data = mRecipeViewModel.getRecipeByName(mRecipeInfo.recipeInfox.RecipeID).getValue();
                RecipeUtils.RecipeInfo recipeInfo = RecipeUtils.recipeDataToInfo(data);
                mRecipeViewModel.setRecipeInfo(recipeInfo);
            }
        }

        mSaveRecipeIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mRecipeSearchResult != null){
                    if(!mIsSaved){
                        RecipeUtils.RecipeInfo recipeInfo = mRecipeViewModel.getRecipeInfo().getValue();
                        mRecipeViewModel.insertRecipe(makeRecipieData(recipeInfo.recipeInfox, recipeInfo.recipeResult) );
                    } else {
                        RecipeUtils.RecipeInfo recipeInfo = mRecipeViewModel.getRecipeInfo().getValue();
                        mRecipeViewModel.deleteRecipe(makeRecipieData(recipeInfo.recipeInfox, recipeInfo.recipeResult));
                    }
                }
                if(mRecipeInfo != null){
                    if(!mIsSaved){
                        mRecipeViewModel.insertRecipe(makeRecipieData(mRecipeInfo.recipeInfox, mRecipeInfo.recipeResult) );
                    } else {
                        mRecipeViewModel.deleteRecipe(makeRecipieData(mRecipeInfo.recipeInfox, mRecipeInfo.recipeResult));
                    }
                }
            }
        });

        mAddIngredientsToListIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add ingredients from recipe to groceryList/ItemDatabase
            }
        });
    }
}