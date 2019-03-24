package com.example.baking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.baking.Items.Recipe;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipesAdapter;

public class RecipeListFragment extends Fragment implements RecipesAdapter.ListItemClickListener {


    public RecipeListFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("TEST", "ATTACH");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("TEST", "DOING FRAGMENT");

        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int defaultInt = getResources().getInteger(R.integer.INT_KEY);
        int clickedOn = sharedPreferences.getInt(getString(R.string.PREF_KEY_CLICKED), defaultInt);
        String defaultString = getResources().getString(R.string.PREF_JSON_DEFAULT);
        String jsonResult = sharedPreferences.getString(getString(R.string.PREF_JSON), defaultString);

        JsonParser jsonParser = new JsonParser(getContext());
        Recipe recipe = jsonParser.parseJsonForRecipe(jsonResult, clickedOn);

        int numberOfHolders = recipe.getRecipeSteps().size();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_RecyclerView3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecipesAdapter recipesAdapter = new RecipesAdapter(numberOfHolders, this, recipe);

        recyclerView.setAdapter(recipesAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
