package com.example.baking.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeIngredients;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.R;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.NumberViewHolder> {

    private ListItemClickListener onClickListener;
    private static int viewHolderCount;
    private int numRecipes;
    private ArrayList<Recipe> recipes;
    private Recipe recipe;
    private Boolean fromMain;
    private ArrayList<RecipeIngredients> mIngredients;
    private ArrayList<RecipeSteps> mSteps;

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

    public RecipesAdapter(int numHolders, ListItemClickListener onClick, Recipe viewRecipe) {
        numRecipes = numHolders;
        onClickListener = onClick;
        recipe = viewRecipe;
        mIngredients = recipe.getRecipeIngredients();
        mSteps = recipe.getRecipeSteps();
        viewHolderCount = 0;
        fromMain = false;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutID = 0;

        if (fromMain) {
            layoutID = R.layout.recipe_item;
        } else {
            layoutID = R.layout.recipe_ingredients;
        }
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
        TextView recipeIngredients;

        public NumberViewHolder(View itemView) {

            super(itemView);

            if (fromMain) {
                recipeItemview = (TextView) itemView.findViewById(R.id.recipe_item);
                itemView.setOnClickListener(this);
            } else {
                recipeIngredients = (TextView) itemView.findViewById(R.id.recipe_ingreds);
            }

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }

        void bind(int listIndex) {

            if (fromMain) {
                recipeItemview.setText(recipes.get(listIndex).getName());
            } else {
                String ingredientsString = "Ingredients: \n\n";


                if (listIndex == 0) {
                    for (int i = 0; i < mIngredients.size(); i++) {
                        ingredientsString = ingredientsString + "-" + mIngredients.get(i).getQuantity() + mIngredients.get(i).getMeasure() + " " + mIngredients.get(i).getIngredient();
                        if (i + 1 < mIngredients.size()) {
                            ingredientsString = ingredientsString + "\n";
                        }
                    }

                    recipeIngredients.setText(ingredientsString);
                } else {
                    recipeIngredients.setText(mSteps.get(listIndex).getShortDescription());
                    recipeIngredients.setOnClickListener(this);
                }
            }
        }
    }
}


