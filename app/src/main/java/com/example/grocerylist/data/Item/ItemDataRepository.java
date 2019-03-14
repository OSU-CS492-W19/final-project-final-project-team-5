package com.example.grocerylist.data.Item;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class ItemDataRepository {
    private ItemDao mItemDao;

    public ItemDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mItemDao = db.locationDao();
    }

    public void insertLocationData(ItemData repo) {
        new InsertAsyncTask(mItemDao).execute(repo);
    }

    public void deleteLocationData(ItemData repo) {
        new DeleteAsyncTask(mItemDao).execute(repo);
    }

    public LiveData<List<ItemData>> getAllItems() {
        return mItemDao.getItems();
    }

    public LiveData<ItemData> getItemByName(String recipie_id) {
        Log.e("newFlag", recipie_id);
        LiveData<ItemData> dat = mItemDao.getItem(recipie_id);
        if (dat.getValue() == null) {
            Log.e("get1", "null");
        }
        else {
            Log.e("get1", dat.getValue().item);
        }
        return dat;
    }

    private static class InsertAsyncTask extends AsyncTask<ItemData, Void, Void> {
        private ItemDao mAsyncTaskDao;
        InsertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ItemData... itemData) {
            Log.e("flagb", itemData[0].item);
            mAsyncTaskDao.insert(itemData[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ItemData, Void, Void> {
        private ItemDao mAsyncTaskDao;
        DeleteAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ItemData... itemData) {
            mAsyncTaskDao.delete(itemData[0]);
            return null;
        }
    }
}