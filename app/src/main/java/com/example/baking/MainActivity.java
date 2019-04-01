package com.example.baking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeIngredients;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipesAdapter;

import java.net.URL;
import java.util.ArrayList;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener {

    private ArrayList<Recipe> mRecipes;
    private RecyclerView mRecipesList;
    private RecipesAdapter mRecipesAdapter;
    private String mJsonResult;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;

        Timber.plant(new Timber.DebugTree());

        JsonParser jsonParser = new JsonParser(getApplicationContext());
        mRecipes = jsonParser.parseJson();
        mJsonResult = jsonParser.getResult();

        Timber.d("Recipe size %s", mRecipes.size());

        mRecipesList = (RecyclerView) findViewById(R.id.recipe_RecyclerView);
        if (getResources().getBoolean(R.bool.Tablet_Check)) {
            Timber.d("This is a TABLET!");
            layoutManager = new GridLayoutManager(context, 3);
        } else {
            layoutManager = new LinearLayoutManager(context);
        }
        mRecipesList.setLayoutManager(layoutManager);
        mRecipesAdapter = new RecipesAdapter(mRecipes.size(), this, mRecipes);
        mRecipesList.setAdapter(mRecipesAdapter);
        mRecipesList.setHasFixedSize(true);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, RecipeActivity.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.PREF_KEY_CLICKED), clickedItemIndex);
        editor.putString(getString(R.string.PREF_JSON), mJsonResult);
        editor.apply();

        startActivity(intent);
    }


}
