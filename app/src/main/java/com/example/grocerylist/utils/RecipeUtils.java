package com.example.grocerylist.utils;

import android.net.Uri;

import com.example.grocerylist.data.Recipe.RecipeData;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;

import com.example.grocerylist.data.recipeSearchResult;


public class RecipeUtils {

//    public static final String EXTRA_FORECAST_ITEM = "com.example.android.lifecycleweather.utils.ForecastItem";

    private final static String RECIPE_BASE_URL = "https://api2.bigoven.com/Recipe";
    private final static String RECIPE_SEARCH_BASE_URL = "https://api2.bigoven.com/Recipes";
    private final static String RECIPE_PAGE_PARAM = "pg";
    private final static String RECIPES_PER_PAGE_PARAM = "rpp";
    private final static String RECIPE_QUERY_PARAM = "Title_kw";
    private final static String RECIPE_APPID_PARAM = "api_key";

    public static final String EXTRA_RECIPE_INFO = "RecipeUtils.RecipeInfo";
    public static final String EXTRA_RECIPE_SEARCH_RESULT = "RecipeUtils.RecipeResult";

    /*
     * Set your own APPID here.
     */
    private final static String APPID = "Rc3kEhQ293h59oU9Z853fw48CmI1H1Js";

    //Recipe search class
    public static class RecipeSearchResult implements Serializable {
        public int ResultCount;
        public RecipeInfox[] Results;
    }
    public static class RecipeInfox implements Serializable {
        public String Category;
        public String Cuisine; //sometimes there
        public String Microcategory;//almost never filled
        public String RecipeID;
        public String ReviewCount;
        public String Servings;
        public String StarRating;
        public String Subcategory;
        public String Title;
    }


    //Recipe Class
    public static class RecipeResult implements Serializable {
        public String ActiveMinutes;
        public String AllCategoriesText;//Pipe seperated list
        public String Category;
        public String Cuisine;
        public String Description;
        public String FavoriteCount;
        public Ingredient[] Ingredients;
        public String Instructions;
        public _NutritionInfo NutritionInfo;
        public String PrimaryIngredient;
        public String RecipeID;

    }
    public static class Ingredient implements Serializable {
        public String DisplayIndex;
        public String DisplayQuantity;
        public String HTMLName;
        public String IngredientID;
        public String MetricDisplayQuantity;
        public String MetricQuantity;
        public String MetricUnit;
        public String Name;
        public String Quantity;
        public String Unit;
        public String PreparationNotes;
    }
    public static class _NutritionInfo implements Serializable {
        public String CaloriesFromFat;
        public String Cholesterol;
        public String CholesterolPct;
        public String DietaryFiber;
        public String DietaryFiberPct;
        public String MonoFat;
        public String PolyFat;
        public String Potassium;
        public String PotassiumPct;
        public String Protein;
        public String ProteinPct;
        public String SatFat;
        public String SatFatPct;
        public String SingularYieldUnit;
        public String Sodium;
        public String SodiumPct;
        public String Sugar;
        public String TotalCalories;
        public String TotalCarbs;
        public String TotalCarbsPct;
        public String TotalFat;
        public String TotalFatPct;
        public String TransFat;
    }

    public static String buildRecipeSearchURL(String title_kw, int page, int Recipes_per_page) {
        return Uri.parse(RECIPE_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(RECIPE_QUERY_PARAM, title_kw)
                .appendQueryParameter(RECIPE_PAGE_PARAM, String.valueOf(page))
                .appendQueryParameter(RECIPES_PER_PAGE_PARAM, String.valueOf(Recipes_per_page))
                .appendQueryParameter(RECIPE_APPID_PARAM, APPID)
                .build()
                .toString();
    }
    public static ArrayList<recipeSearchResult> parseRecipeSearchJson(String json) {
        Gson gson = new Gson();
        RecipeSearchResult result = gson.fromJson(json, RecipeSearchResult.class);
        if (result != null && result.Results != null) {
            ArrayList<recipeSearchResult> Results = new ArrayList<>();

            for(RecipeInfox info : result.Results){
                recipeSearchResult recipesearchsesult = new recipeSearchResult();

                recipesearchsesult.Category = info.Category;
                recipesearchsesult.Cuisine = info.Cuisine;
                recipesearchsesult.Microcategory = info.Microcategory;
                recipesearchsesult.RecipeID = info.RecipeID;
                recipesearchsesult.ReviewCount = info.ReviewCount;
                recipesearchsesult.Servings = info.Servings;
                recipesearchsesult.StarRating = info.StarRating;
                recipesearchsesult.Subcategory = info.Subcategory;
                recipesearchsesult.Title = info.Title;

                Results.add(recipesearchsesult);
            }
            return Results;
        } else {
            return null;
        }
    }


    public static String buildRecipeURL(String Recipe_id) {
        return Uri.parse(RECIPE_BASE_URL + "/" + Recipe_id).buildUpon()
                .appendQueryParameter(RECIPE_APPID_PARAM, APPID)
                .build()
                .toString();
    }
    public static RecipeResult parseRecipeJson(String json) {
        Gson gson = new Gson();
        RecipeResult result = gson.fromJson(json, RecipeResult.class);
        if (result != null && result != null) {
            return result;
        } else {
            return null;
        }
    }

    public static RecipeData makeRecipieData(RecipeInfox recipeInfox, RecipeResult recipeResult) {
        RecipeData recipeData = new RecipeData();
        recipeData.recipe_id = recipeInfox.RecipeID;

        Gson gson = new Gson();
        recipeData.recipe_infox_json = gson.toJson(recipeInfox);
        recipeData.recipe_result_json = gson.toJson(recipeResult);

        return recipeData;
    }

    public static class RecipeInfo implements Serializable{
        public RecipeInfox recipeInfox;
        public RecipeResult recipeResult;
    }

}
