package com.example.baking;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeIngredients;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.Utils.JsonParser;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonParser jsonParser = new JsonParser(getApplicationContext());
        mRecipes = jsonParser.parseJson();
        ArrayList<RecipeSteps> steps = mRecipes.get(0).getRecipeSteps();

        for (int i = 0; i < steps.size(); i++) {
            Log.d("TEST", "Short: " + steps.get(i).getShortDescription());
            Log.d("TEST", "Long: " + steps.get(i).getDescription());
            Log.d("TEST", "VideoURL " + steps.get(i).getVideoURL());
            Log.d("TEST", "Thumb URL " + steps.get(i).getThumbnailURL());
        }

    }
}
