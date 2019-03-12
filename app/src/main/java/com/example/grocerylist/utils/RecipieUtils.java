package com.example.grocerylist.utils;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import java.io.Serializable;


public class RecipieUtils {

//    public static final String EXTRA_FORECAST_ITEM = "com.example.android.lifecycleweather.utils.ForecastItem";

    private final static String RECIPIE_BASE_URL = "https://api2.bigoven.com/Recipe";
    private final static String RECIPIE_SEARCH_BASE_URL = "https://api2.bigoven.com/Recipes";
    private final static String RECIPIE_PAGE_PARAM = "pg";
    private final static String RECIPES_PER_PAGE_PARAM = "rpp";
    private final static String RECIPIE_QUERY_PARAM = "Title_kw";
    private final static String RECIPIE_APPID_PARAM = "api_key";

    /*
     * Set your own APPID here.
     */
    private final static String APPID = "Rc3kEhQ293h59oU9Z853fw48CmI1H1Js";

    //Recipie search class
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


    //Recipie Class
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

    public static String buildRecipieSearchURL(String title_kw, int page, int recipies_per_page) {
        return Uri.parse(RECIPIE_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(RECIPIE_QUERY_PARAM, title_kw)
                .appendQueryParameter(RECIPIE_PAGE_PARAM, String.valueOf(page))
                .appendQueryParameter(RECIPES_PER_PAGE_PARAM, String.valueOf(recipies_per_page))
                .appendQueryParameter(RECIPIE_APPID_PARAM, APPID)
                .build()
                .toString();
    }
    public static RecipeSearchResult parseRecipieSearchJson(String json) {
        Gson gson = new Gson();
        RecipeSearchResult result = gson.fromJson(json, RecipeSearchResult.class);
        if (result != null && result != null) {
            return result;
        } else {
            return null;
        }
    }


    public static String buildRecipieURL(String recipie_id) {
        return Uri.parse(RECIPIE_BASE_URL + "/" + recipie_id).buildUpon()
                .appendQueryParameter(RECIPIE_APPID_PARAM, APPID)
                .build()
                .toString();
    }
    public static RecipeResult parseRecipieJson(String json) {
        Gson gson = new Gson();
        RecipeResult result = gson.fromJson(json, RecipeResult.class);
        if (result != null && result != null) {
            return result;
        } else {
            return null;
        }
    }


}
