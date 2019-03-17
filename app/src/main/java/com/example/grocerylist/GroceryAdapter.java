package com.example.grocerylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.grocerylist.data.Item.ItemData;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    //private ArrayList<String> mGroceryList;
    private List<ItemData> mGroceryItems;
    private OnItemCheckedChangeListener mCheckedChangeListener;

    public interface OnItemCheckedChangeListener{
        void onItemCheckedChanged(ItemData item, boolean b);
    }

    GroceryAdapter(OnItemCheckedChangeListener checkedChangeListener){
        mCheckedChangeListener = checkedChangeListener;
    }

    public List<ItemData> getmGroceryItems() {
        return mGroceryItems;
    }

    /* public void addItem(String item){
        mGroceryList.add(item);
        notifyItemInserted(0);
    }*/

    public void updateGroceryList(List<ItemData> items) {
        mGroceryItems = items;
        notifyDataSetChanged();
    }

 /*   @Override
    public int getItemCount(){
        return mGroceryList.size();
    }*/

    @Override
    public int getItemCount() {
        if (mGroceryItems != null) {
            return mGroceryItems.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.grocery_list_item, viewGroup, false);
        return new GroceryViewHolder(itemView);
    }

    public ItemData getItemAtPosition(int position){
        return mGroceryItems.get(position);
    }

 /*   @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int i) {
        String item = mGroceryList.get(adapterPositionToArrayIndex(i));
        groceryViewHolder.bind(item);
    }*/

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        holder.bind(mGroceryItems.get(position));
    }

 /*   public int adapterPositionToArrayIndex(int i){
        return mGroceryList.size() - i -1;
    }*/

    class GroceryViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemTV;

        public GroceryViewHolder(final View itemView){
            super(itemView);
            mItemTV = itemView.findViewById(R.id.tv_item_text);

            CheckBox checkBox = itemView.findViewById(R.id.item_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                    ItemData item = mGroceryItems.get(getAdapterPosition());
                    mCheckedChangeListener.onItemCheckedChanged(item, b);
                }
            });
        }

        public void bind(ItemData item){
            mItemTV.setText(item.item);
        }

    /*    public void removeFromList(){
            int position = getAdapterPosition();
            mGroceryList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);

        }*/
    }
}

