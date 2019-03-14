package com.example.grocerylist.data.Recipie;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class RecipieDataRepository {
    private RecipieDao mRecipieDao;

    public RecipieDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRecipieDao = db.locationDao();
    }

    public void insertLocationData(RecipieData repo) {
        new InsertAsyncTask(mRecipieDao).execute(repo);
    }

    public void deleteLocationData(RecipieData repo) {
        new DeleteAsyncTask(mRecipieDao).execute(repo);
    }

    public LiveData<List<RecipieData>> getAllRecipies() {
        return mRecipieDao.getRecipies();
    }

    public LiveData<RecipieData> getRecipieByName(String recipie_id) {
        Log.e("newFlag", recipie_id);
        LiveData<RecipieData> dat = mRecipieDao.getRecipie(recipie_id);
        if (dat.getValue() == null) {
            Log.e("get1", "null");
        }
        else {
            Log.e("get1", dat.getValue().recipie_id);
        }
        return dat;
    }

    private static class InsertAsyncTask extends AsyncTask<RecipieData, Void, Void> {
        private RecipieDao mAsyncTaskDao;
        InsertAsyncTask(RecipieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RecipieData... recipieData) {
            Log.e("flagb", recipieData[0].recipie_id);
            mAsyncTaskDao.insert(recipieData[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<RecipieData, Void, Void> {
        private RecipieDao mAsyncTaskDao;
        DeleteAsyncTask(RecipieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RecipieData... recipieData) {
            mAsyncTaskDao.delete(recipieData[0]);
            return null;
        }
    }
}