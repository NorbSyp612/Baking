package com.example.baking.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeIngredients;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

public class JsonParser {

    private Context mContext;
    private URL mUrl;
    private String result;

    public JsonParser(Context context) {
        mContext = context;
    }

    public String getResult() {
        return result;
    }

    public ArrayList<Recipe> parseJson() {
        String urlString = mContext.getString(R.string.json_url);
        mUrl = null;

        try {
            mUrl = new URL(urlString);
        } catch (MalformedURLException e) {
        }

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String jsonResult = "";

        try {
            jsonResult = new getJsonString().execute(mUrl).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {

            if (jsonResult == null) {
                return recipes;
            }

            result = jsonResult;

            JSONArray jRecipes = new JSONArray(jsonResult);

            for (int i = 0; i < jRecipes.length(); i++) {
                Recipe addRecipe = new Recipe();
                ArrayList<RecipeIngredients> listOfIngredients = new ArrayList<>();
                ArrayList<RecipeSteps> listOfSteps = new ArrayList<>();
                JSONObject recipe = jRecipes.getJSONObject(i);

                String recipeID = recipe.getString("id");
                String recipeName = recipe.getString("name");
                String recipeServings = recipe.getString("servings");
                String recipeImageURL = recipe.getString("image");

                JSONArray jIngredients = recipe.getJSONArray("ingredients");
                for (int a = 0; a < jIngredients.length(); a++) {
                    RecipeIngredients addIngredients = new RecipeIngredients();
                    JSONObject ingredients = jIngredients.getJSONObject(a);

                    String quantity = ingredients.getString("quantity");
                    String measure = ingredients.getString("measure");
                    String ingredient = ingredients.getString("ingredient");

                    addIngredients.setQuantity(quantity);
                    addIngredients.setMeasure(measure);
                    addIngredients.setIngredient(ingredient);

                    listOfIngredients.add(addIngredients);
                }

                JSONArray jSteps = recipe.getJSONArray("steps");
                for (int b = 0; b < jSteps.length(); b++) {
                    RecipeSteps addSteps = new RecipeSteps();
                    JSONObject steps = jSteps.getJSONObject(b);

                    String stepId = steps.getString("id");
                    String shortDescrption = steps.getString("shortDescription");
                    String descrption = steps.getString("description");
                    String videoURL = steps.getString("videoURL");
                    String thumbnailURL = steps.getString("thumbnailURL");

                    addSteps.setId(stepId);
                    addSteps.setShortDescription(shortDescrption);
                    addSteps.setDescription(descrption);
                    addSteps.setVideoURL(videoURL);
                    addSteps.setThumbnailURL(thumbnailURL);

                    listOfSteps.add(addSteps);
                }

                addRecipe.setId(recipeID);
                addRecipe.setName(recipeName);
                addRecipe.setServings(recipeServings);
                addRecipe.setImage(recipeImageURL);
                addRecipe.setRecipeIngredients(listOfIngredients);
                addRecipe.setRecipeSteps(listOfSteps);
                recipes.add(addRecipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recipes;
    }

    public Recipe parseJsonForRecipe(String jsonResult, int position) {
        Recipe addRecipe = new Recipe();

        try {

            if (jsonResult == null) {
                return addRecipe;
            }

            JSONArray jRecipes = new JSONArray(jsonResult);

            ArrayList<RecipeIngredients> listOfIngredients = new ArrayList<>();
            ArrayList<RecipeSteps> listOfSteps = new ArrayList<>();
            JSONObject recipe = jRecipes.getJSONObject(position);

            String recipeID = recipe.getString("id");
            String recipeName = recipe.getString("name");
            String recipeServings = recipe.getString("servings");
            String recipeImageURL = recipe.getString("image");

            JSONArray jIngredients = recipe.getJSONArray("ingredients");
            for (int a = 0; a < jIngredients.length(); a++) {
                RecipeIngredients addIngredients = new RecipeIngredients();
                JSONObject ingredients = jIngredients.getJSONObject(a);

                String quantity = ingredients.getString("quantity");
                String measure = ingredients.getString("measure");
                String ingredient = ingredients.getString("ingredient");

                addIngredients.setQuantity(quantity);
                addIngredients.setMeasure(measure);
                addIngredients.setIngredient(ingredient);

                listOfIngredients.add(addIngredients);
            }

            JSONArray jSteps = recipe.getJSONArray("steps");
            for (int b = 0; b < jSteps.length(); b++) {
                RecipeSteps addSteps = new RecipeSteps();
                JSONObject steps = jSteps.getJSONObject(b);

                String stepId = steps.getString("id");
                String shortDescrption = steps.getString("shortDescription");
                String descrption = steps.getString("description");
                String videoURL = steps.getString("videoURL");
                String thumbnailURL = steps.getString("thumbnailURL");

                addSteps.setId(stepId);
                addSteps.setShortDescription(shortDescrption);
                addSteps.setDescription(descrption);
                addSteps.setVideoURL(videoURL);
                addSteps.setThumbnailURL(thumbnailURL);

                listOfSteps.add(addSteps);
            }

            addRecipe.setId(recipeID);
            addRecipe.setName(recipeName);
            addRecipe.setServings(recipeServings);
            addRecipe.setImage(recipeImageURL);
            addRecipe.setRecipeIngredients(listOfIngredients);
            addRecipe.setRecipeSteps(listOfSteps);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return addRecipe;
    }

    public static class getJsonString extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL jsonURL = urls[0];
            String apiResult = null;

            try {
                apiResult = NetworkUtils.getResponseFromHttpUrl(jsonURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return apiResult;
        }

        @Override
        protected void onPostExecute(String apiResults) {

        }
    }

}
