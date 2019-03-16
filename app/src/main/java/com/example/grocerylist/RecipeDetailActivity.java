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

import com.example.grocerylist.data.Recipe.RecipeData;
import com.example.grocerylist.utils.RecipeUtils;

public class RecipeDetailActivity extends AppCompatActivity{

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private TextView mRecipeNameTV;
    private TextView mRecipeInstructionsTV;
    private ListView mRecipeIngredients;

    private ImageView mAddIngredientsToListIV;
    private ImageView mSaveRecipeIV;

    private String mRecipeId;
    private String mRecipeInfoxJson;
    private String mRecipeResultJson;
    private RecipeUtils.RecipeResult mRecipeResult;
    private RecipeAdapter mAdapter;

    private RecipeViewModel mRecipeViewModel;
    private RecipeData mRecipe;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipeNameTV = findViewById(R.id.tv_recipe_name);
        mRecipeIngredients = findViewById(R.id.lv_ingredients);
        mRecipeInstructionsTV = findViewById(R.id.tv_instructions);

        mAddIngredientsToListIV = findViewById(R.id.iv_add_items_to_list);
        mSaveRecipeIV = findViewById(R.id.iv_save_recipe);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        mRecipe = null;
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(RecipeUtils.EXTRA_RECIPE)){
            Log.d(TAG, "Intent is not null.");
            mRecipe = (RecipeData) intent.getSerializableExtra(RecipeUtils.EXTRA_RECIPE);
            mRecipeId = mRecipe.recipe_id;
            mRecipeInfoxJson = mRecipe.recipe_infox_json;
            mRecipeResultJson = mRecipe.recipe_result_json;

            mRecipeResult = RecipeUtils.parseRecipeJson(mRecipeResultJson);
            mRecipeInstructionsTV.setText(mRecipeResult.Instructions);
            //TODO: parse RecipeData, RecipeInfo, RecipeInfox, Ingredients, etc.

            mRecipeViewModel.getRecipeByName(mRecipe.recipe_id).observe(this, new Observer<RecipeData>() {
                @Override
                public void onChanged(@Nullable RecipeData recipeData) {
                    if(recipeData != null){
                        mIsSaved = true;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_black_24dp);
                    } else {
                        mIsSaved = false;
                        mSaveRecipeIV.setImageResource(R.drawable.ic_save_transparent);
                    }
                }
            });
        }

        mSaveRecipeIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mRecipe != null){
                    if(!mIsSaved){
                        mRecipeViewModel.insertRecipe(mRecipe);
                    } else {
                        mRecipeViewModel.deleteRecipe(mRecipe);
                    }
                }
            }
        });
    }
}