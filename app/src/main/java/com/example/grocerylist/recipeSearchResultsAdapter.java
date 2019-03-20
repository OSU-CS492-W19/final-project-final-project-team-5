package com.example.grocerylist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import android.support.v7.widget.RecyclerView;

import com.example.grocerylist.data.recipeSearchResult;

public class recipeSearchResultsAdapter extends RecyclerView.Adapter<recipeSearchResultsAdapter.recipeSearchResultsViewHolder> {

    private List<recipeSearchResult> mSearchResults;
    private OnResultItemClickListener mResultItemClickListener;

    public interface OnResultItemClickListener {
        void onResultItemClick(recipeSearchResult recipesearchresult);
    }

    public recipeSearchResultsAdapter(OnResultItemClickListener clickListener) {
        mResultItemClickListener = clickListener;
    }

    public void updateResults(List<recipeSearchResult> results) {
        mSearchResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchResults != null) {
            return mSearchResults.size();
        } else {
            return 0;
        }
    }

    @Override
    public recipeSearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.saved_recipe_item, parent, false);
        return new recipeSearchResultsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(recipeSearchResultsViewHolder holder, int position) {
        holder.bind(mSearchResults.get(position));
    }

    class recipeSearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mCategory;
        private TextView mCuisine; //sometimes there
        private TextView mReviewCount;
        private TextView mServings;
        private TextView mStarRating;

        public recipeSearchResultsViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_recipe_title);
            mCategory = itemView.findViewById(R.id.tv_recipe_category);;
            mCuisine = itemView.findViewById(R.id.tv_recipe_cuisine); //sometimes there
            mReviewCount = itemView.findViewById(R.id.tv_recipe_review_count);
            mServings = itemView.findViewById(R.id.tv_recipe_servings);
            mStarRating = itemView.findViewById(R.id.tv_recipe_star_rating);
            itemView.setOnClickListener(this);
        }

        public void bind(recipeSearchResult result) {
            mTitle.setText(result.Title);
            if (result.Category != null && result.Category.trim().length() > 0 ) {
                mCategory.setText(result.Category);
            }else {
                mCategory.setVisibility(View.GONE);
            }
            if (result.Cuisine != null && result.Cuisine.trim().length() > 0 ) {
                mCuisine.setText(result.Cuisine);
            }else {
                mCuisine.setVisibility(View.GONE);
            }

            mReviewCount.setText(result.ReviewCount);
            mServings.setText(result.Servings);
            mStarRating.setText(result.StarRating);

        }

        @Override
        public void onClick(View v) {
            recipeSearchResult result = mSearchResults.get(getAdapterPosition());
            mResultItemClickListener.onResultItemClick(result);
        }
    }
}
