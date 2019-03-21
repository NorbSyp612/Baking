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

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.NumberViewHolder> {

    private ListItemClickListener onClickListener;
    private static int viewHolderCount;
    private int numberMovies;
    private ArrayList<Recipe> recipes;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecipesAdapter (ListItemClickListener onClick, ArrayList<Recipe> recipesArray) {
        onClickListener = onClick;
        this.recipes = recipesArray;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.recipe_item;
        LayoutInflater inflater LayoutInflater.from(context);
        boolean shouldAttachtoParentImm = false;

        View view = inflater.inflate(layoutID, viewGroup, shouldAttachtoParentImm);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolderCount++;
        return viewHolder;
    }


}


