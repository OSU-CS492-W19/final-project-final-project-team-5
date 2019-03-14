package com.example.grocerylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grocerylist.data.Recipe;

import java.util.List;

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.SearchResultViewHolder> {
    private List<Recipe> mRecipes;
    OnSearchItemClickListener mSearchRecipeClickListener;

    public interface OnSearchItemClickListener{
        void onSearchItemClick(Recipe recipe);
    }

    RecipeSearchAdapter(OnSearchItemClickListener searchItemClickListener){
        mSearchRecipeClickListener = searchItemClickListener;
    }

    public void updateSearchResults(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (mRecipes != null){
            return mRecipes.size();
        }else{
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_recipe_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position){
        holder.bind(mRecipes.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder{
        private TextView mSearchResultTV;

        public SearchResultViewHolder(View itemView){
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_recipe_result_item);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Recipe searchResult = mRecipes.get(getAdapterPosition());
                    mSearchRecipeClickListener.onSearchItemClick(searchResult);
                }
            });
        }

        public void bind(Recipe recipe){
            mSearchResultTV.setText(recipe.recipe_name);
        }
    }
}
