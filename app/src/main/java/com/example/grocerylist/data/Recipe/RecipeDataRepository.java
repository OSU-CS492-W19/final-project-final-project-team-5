package com.example.grocerylist.data.Recipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.grocerylist.data.Status;
import com.example.grocerylist.utils.RecipeUtils;

import java.util.List;

public class RecipeDataRepository implements LoadRecipeTask.AsyncCallback{

    private static String TAG = RecipeDataRepository.class.getSimpleName();

    private RecipeDao mRecipeDao;
    private MutableLiveData<Status> mLoadingStatus;

    public RecipeDataRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        mRecipeDao = db.RecipeDao();

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
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

    public void loadRecipeSearch(String id) {
        RecipeData tempRecipe = getRecipeByName(id).getValue();
        if(tempRecipe != null){
            if (tempRecipe.recipe_id.equals(id)) {
                Log.d(TAG, "recipe already in database");
            }
        } else {
            String url = RecipeUtils.buildRecipeURL(id);
            Log.d(TAG, "fetching new recipe data with this URL: " + url);
            new LoadRecipeTask(url, this).execute();
        }
    }

    @Override
    public void onRecipeLoadFinished(RecipeData recipe) {
        if (recipe != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
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
        protected Void doInBackground(RecipeData... recipeData) {
            mAsyncTaskDao.delete(recipeData[0]);
            return null;
        }
    }
}