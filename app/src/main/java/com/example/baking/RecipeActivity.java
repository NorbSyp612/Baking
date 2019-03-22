package com.example.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.baking.Items.Recipe;
import com.example.baking.Utils.JsonParser;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private ArrayList<Recipe> mRecipes;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();

        JsonParser jsonParser = new JsonParser(getApplicationContext());
        mRecipes = jsonParser.parseJson();

        String position = intent.getStringExtra("TEST");
        int pos = Integer.parseInt(position);

        mRecipe = mRecipes.get(pos);


    }

}
