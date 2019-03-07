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

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private ArrayList<String> mGroceryList;
    private OnItemCheckedChangeListener mCheckedChangeListener;

    public interface OnItemCheckedChangeListener{
        void onItemCheckedChanged(String item, boolean b);
    }

    public GroceryAdapter(OnItemCheckedChangeListener checkedChangeListener){
        mGroceryList = new ArrayList<>();
        mCheckedChangeListener = checkedChangeListener;
    }

    public void addItem(String item){
        mGroceryList.add(item);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount(){
        return mGroceryList.size();
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.grocery_list_item, viewGroup, false);
        return new GroceryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int i) {
        String item = mGroceryList.get(adapterPositionToArrayIndex(i));
        groceryViewHolder.bind(item);
    }

    public int adapterPositionToArrayIndex(int i){
        return mGroceryList.size() - i -1;
    }

    class GroceryViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemTV;

        public GroceryViewHolder(final View itemView){
            super(itemView);
            mItemTV = itemView.findViewById(R.id.tv_item_text);

            CheckBox checkBox = itemView.findViewById(R.id.item_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                    String item = mGroceryList.get(adapterPositionToArrayIndex(getAdapterPosition()));
                    mCheckedChangeListener.onItemCheckedChanged(item, b);
                }
            });
        }

        public void bind(String item){
            mItemTV.setText(item);
        }

        public void removeFromList(){
            int position = getAdapterPosition();
            mGroceryList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);
        }
    }
}

