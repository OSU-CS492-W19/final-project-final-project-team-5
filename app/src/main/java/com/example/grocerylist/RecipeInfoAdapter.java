package com.example.grocerylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerylist.utils.RecipeUtils;

import java.util.ArrayList;

public class RecipeInfoAdapter extends RecyclerView.Adapter<RecipeInfoAdapter.RecipeViewHolder> {
   // private ArrayList<RecipeUtils.RecipeInfo> mRecipeList;
    private ArrayList<RecipeUtils.RecipeInfo> mRecipeList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(RecipeUtils.RecipeInfo recipeInfo);
    }

    public RecipeInfoAdapter(OnItemClickListener itemClickListener){
        mRecipeList = new ArrayList<RecipeUtils.RecipeInfo>();
        mItemClickListener = itemClickListener;
    }

    public void updateItems(ArrayList<RecipeUtils.RecipeInfo> items) {
        mRecipeList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return mRecipeList.size();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.saved_recipe_item, viewGroup, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder RecipeViewHolder, int i) {
        RecipeUtils.RecipeInfo item = mRecipeList.get(adapterPositionToArrayIndex(i));
        RecipeViewHolder.bind(item);
    }

    public int adapterPositionToArrayIndex(int i){
        return mRecipeList.size() - i -1;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitle;
        private TextView mCategory;
        private TextView mCuisine; //sometimes there
        private TextView mReviewCount;
        private TextView mServings;
        private TextView mStarRating;

        public RecipeViewHolder(final View itemView){
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_recipe_title);
            mCategory = itemView.findViewById(R.id.tv_recipe_category);;
            mCuisine = itemView.findViewById(R.id.tv_recipe_cuisine); //sometimes there
            mReviewCount = itemView.findViewById(R.id.tv_recipe_review_count);
            mServings = itemView.findViewById(R.id.tv_recipe_servings);
            mStarRating = itemView.findViewById(R.id.tv_recipe_star_rating);

            itemView.setOnClickListener(this);

        }

        public void bind(RecipeUtils.RecipeInfo item){

            mTitle.setText(item.recipeInfox.Title);
            if (item.recipeInfox.Category != null && item.recipeInfox.Category.trim().length() > 0 ) {
                mCategory.setText(item.recipeInfox.Category);
            }else {
                mCategory.setVisibility(View.GONE);
            }
            if (item.recipeInfox.Cuisine != null && item.recipeInfox.Cuisine.trim().length() > 0 ) {
                mCuisine.setText(item.recipeInfox.Cuisine);
            }else {
                mCuisine.setVisibility(View.GONE);
            }

            mReviewCount.setText(item.recipeInfox.ReviewCount);
            mServings.setText(item.recipeInfox.Servings);
            mStarRating.setText(item.recipeInfox.StarRating);

        }

        public void removeFromList(){
            int position = getAdapterPosition();
            mRecipeList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);

        }

        @Override
        public void onClick(View v) {
            RecipeUtils.RecipeInfo recipeInfo = mRecipeList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mItemClickListener.onItemClicked(recipeInfo);
        }
    }
}

