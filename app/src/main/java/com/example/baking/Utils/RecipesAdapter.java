package com.example.baking.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.Items.Recipe;
import com.example.baking.R;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.NumberViewHolder> {

    private ListItemClickListener onClickListener;
    private static int viewHolderCount;
    private int numRecipes;
    private ArrayList<Recipe> recipes;
    private Boolean fromMain;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecipesAdapter(int recipes, ListItemClickListener onClick, ArrayList<Recipe> recipesArray) {
        numRecipes = recipes;
        onClickListener = onClick;
        this.recipes = recipesArray;
        viewHolderCount = 0;
        fromMain = true;
    }

    public void setBooleanFromMain(Boolean fromWhere) {
        fromMain = fromWhere;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutID, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return numRecipes;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int i) {
        holder.bind(i);
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeItemview;

        public NumberViewHolder(View itemView) {

            super(itemView);

            if (fromMain) {
                recipeItemview = (TextView) itemView.findViewById(R.id.recipe_item);
                itemView.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }

        void bind(int listIndex) {
            Context context = itemView.getContext();
            recipeItemview.setText(recipes.get(listIndex).getName());
        }
    }
}


