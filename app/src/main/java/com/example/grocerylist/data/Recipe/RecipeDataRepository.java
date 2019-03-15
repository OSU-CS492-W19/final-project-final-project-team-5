package com.example.grocerylist.data.Recipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class RecipeDataRepository {
    private RecipeDao mRecipieDao;

    public RecipeDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRecipieDao = db.locationDao();
    }

    public void insertRecipeData(RecipeData repo) {
        new InsertAsyncTask(mRecipieDao).execute(repo);
    }

    public void deleteRecipeData(RecipeData repo) {
        new DeleteAsyncTask(mRecipieDao).execute(repo);
    }

    public LiveData<List<RecipeData>> getAllRecipes() {
        return mRecipieDao.getRecipes();
    }

    public LiveData<RecipeData> getRecipeById(String recipie_id) {
        Log.e("newFlag", recipie_id);
        LiveData<RecipeData> dat = mRecipieDao.getRecipe(recipie_id);
        if (dat.getValue() == null) {
            Log.e("get1", "null");
        }
        else {
            Log.e("get1", dat.getValue().recipie_id);
        }
        return dat;
    }

    private static class InsertAsyncTask extends AsyncTask<RecipeData, Void, Void> {
        private RecipeDao mAsyncTaskDao;
        InsertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RecipeData... recipieData) {
            Log.e("flagb", recipieData[0].recipie_id);
            mAsyncTaskDao.insert(recipieData[0]);
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