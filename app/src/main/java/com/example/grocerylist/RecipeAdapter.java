package com.example.grocerylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.grocerylist.utils.RecipeUtils;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipieViewHolder> {
    private ArrayList<RecipeUtils.RecipeInfo> mRecipieList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(RecipeUtils.RecipeInfo recipeInfo);
    }

    public RecipeAdapter(OnItemClickListener itemClickListener){
        mRecipieList = new ArrayList<RecipeUtils.RecipeInfo>();
        mItemClickListener = itemClickListener;
    }

    public void updateItems(ArrayList<RecipeUtils.RecipeInfo> items) {
        mRecipieList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return mRecipieList.size();
    }

    @NonNull
    @Override
    public RecipieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.saved_recipe_item, viewGroup, false);////TODO : Use Same xml as the search activity
        return new RecipieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipieViewHolder recipieViewHolder, int i) {
        RecipeUtils.RecipeInfo item = mRecipieList.get(adapterPositionToArrayIndex(i));
        recipieViewHolder.bind(item);
    }

    public int adapterPositionToArrayIndex(int i){
        return mRecipieList.size() - i -1;
    }

    class RecipieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitle;
        private TextView mCategory;
        private TextView mCuisine; //sometimes there
        private TextView mMicrocategory;//almost never filled
        private TextView mReviewCount;
        private TextView mServings;
        private TextView mStarRating;
        private TextView mSubcategory;

        public RecipieViewHolder(final View itemView){
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_recipe_title);
            mCategory = itemView.findViewById(R.id.tv_recipe_category);;
            mCuisine = itemView.findViewById(R.id.tv_recipe_cuisine); //sometimes there
            mMicrocategory = itemView.findViewById(R.id.tv_recipe_microcategory);//almost never filled
            mReviewCount = itemView.findViewById(R.id.tv_recipe_review_count);
            mServings = itemView.findViewById(R.id.tv_recipe_servings);
            mStarRating = itemView.findViewById(R.id.tv_recipe_star_rating);
            mSubcategory = itemView.findViewById(R.id.tv_recipe_subcategory);
            itemView.setOnClickListener(this);

        }

        public void bind(RecipeUtils.RecipeInfo item){

            mTitle.setText(item.recipeInfox.Title);
            mCategory.setText(item.recipeInfox.Category);
            mCuisine.setText(item.recipeInfox.Cuisine);
            mMicrocategory.setText(item.recipeInfox.Microcategory);//almost never filled
            mReviewCount.setText(item.recipeInfox.ReviewCount);
            mServings.setText(item.recipeInfox.Servings);
            mStarRating.setText(item.recipeInfox.StarRating);
            mSubcategory.setText(item.recipeInfox.Subcategory);

        }

        public void removeFromList(){
            int position = getAdapterPosition();
            mRecipieList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);

        }

        @Override
        public void onClick(View v) {
            RecipeUtils.RecipeInfo recipeInfo = mRecipieList.get(getAdapterPosition());
            mItemClickListener.onItemClicked(recipeInfo);
        }
    }
}

