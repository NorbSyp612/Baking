package com.example.baking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.baking.Items.Recipe;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipesAdapter;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener {

    private ArrayList<Recipe> mRecipes;
    private Recipe mRecipe;
    private RecyclerView mRecylerView;
    private RecipesAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        Context context = getApplicationContext();

        JsonParser jsonParser = new JsonParser(getApplicationContext());


        String position = intent.getStringExtra("TEST");
        String jsonResult = intent.getStringExtra("TEST2");
        int pos = Integer.parseInt(position);

        mRecipe = jsonParser.parseJsonForRecipe(jsonResult, pos);

        mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecylerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipesAdapter(1, this, mRecipe);
        mRecylerView.setAdapter(mRecipeAdapter);
        mRecylerView.setHasFixedSize(true);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
