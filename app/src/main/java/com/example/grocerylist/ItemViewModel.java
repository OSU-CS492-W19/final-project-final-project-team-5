package com.example.grocerylist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import com.example.grocerylist.data.Item.ItemData;
import com.example.grocerylist.data.Item.ItemDataRepository;

public class ItemViewModel extends AndroidViewModel {

    private ItemDataRepository mItemDataRepository;

    public ItemViewModel(Application application) {
        super(application);
        mItemDataRepository = new ItemDataRepository(application);
    }

    public void insertItemData(ItemData item) {
        mItemDataRepository.insertItemData(item);
    }

    public void deleteItemData(ItemData item) {
        mItemDataRepository.deleteItemData(item);
    }

    public LiveData<List<ItemData>> getAllItems() {
        return mItemDataRepository.getAllItems();
    }

    public LiveData<ItemData> getItemByName(String ItemData) {
        return mItemDataRepository.getItemByName(ItemData);
    }
}
