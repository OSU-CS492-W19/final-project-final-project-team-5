package com.example.grocerylist.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RecipeRepository {
    private RecipeDao mRecipeDao;

    public RecipeRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
    }

    public void insertRecipe(Recipe recipe){
        new InsertAsyncTask(mRecipeDao).execute(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        new DeleteAsyncTask(mRecipeDao).execute(recipe);
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return mRecipeDao.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeByName(String name){
        return mRecipeDao.getRecipeByName(name);
    }

    private static class InsertAsyncTask extends AsyncTask<Recipe, Void, Void>{
        private RecipeDao mAsyncTaskDao;
        InsertAsyncTask(RecipeDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes){
            mAsyncTaskDao.insert(recipes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Recipe, Void, Void>{
        private RecipeDao mAsyncTaskDao;
        DeleteAsyncTask(RecipeDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes){
            mAsyncTaskDao.delete(recipes[0]);
            return null;
        }
    }
}
