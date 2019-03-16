package com.example.grocerylist.data.Recipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class RecipeDataRepository {
    private RecipeDao mRecipeDao;

    public RecipeDataRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        mRecipeDao = db.RecipeDao();
    }

    public void insertRecipeData(RecipeData repo) {
        new InsertAsyncTask(mRecipeDao).execute(repo);
    }

    public void deleteRecipeData(RecipeData repo) {
        new DeleteAsyncTask(mRecipeDao).execute(repo);
    }

    public LiveData<List<RecipeData>> getAllRecipes() {
        return mRecipeDao.getRecipes();
    }

    public LiveData<RecipeData> getRecipeByName(String recipe_id) {
        Log.e("newFlag", recipe_id);
        LiveData<RecipeData> dat = mRecipeDao.getRecipe(recipe_id);
        if (dat.getValue() == null) {
            Log.e("get1", "null");
        }
        else {
            Log.e("get1", dat.getValue().recipe_id);
        }
        return dat;
    }

    private static class InsertAsyncTask extends AsyncTask<RecipeData, Void, Void> {
        private RecipeDao mAsyncTaskDao;
        InsertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RecipeData... recipeData) {
            Log.e("flagb", recipeData[0].recipe_id);
            mAsyncTaskDao.insert(recipeData[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<RecipeData, Void, Void> {
        private RecipeDao mAsyncTaskDao;
        DeleteAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RecipeData... recipieData) {
            mAsyncTaskDao.delete(recipieData[0]);
            return null;
        }
    }
}