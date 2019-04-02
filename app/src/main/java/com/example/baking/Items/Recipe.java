package com.example.baking.Items;

import java.util.ArrayList;

public class Recipe {

    private String id;
    private String name;
    private ArrayList<RecipeIngredients> recipeIngredients;
    private ArrayList<RecipeSteps> recipeSteps;
    private String servings;
    private String image;

    public Recipe() {}

    public void setImage(String ImageFromJson) {
        image = ImageFromJson;
    }

    public String getImage() {
        return image;
    }

    public void setId(String IDFromJson) {
        id = IDFromJson;
    }

    public String getId() {
        return id;
    }

    public void setName(String NameFromJSON) {
        name = NameFromJSON;
    }

    public String getName() {
        return name;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredients> RecipeIngredientsFromJSON){
        recipeIngredients = RecipeIngredientsFromJSON;
    }

    public ArrayList<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeSteps(ArrayList<RecipeSteps> RecipeStepsFromJSON) {
        recipeSteps = RecipeStepsFromJSON;
    }

    public ArrayList<RecipeSteps> getRecipeSteps() {
        return recipeSteps;
    }

    public void setServings(String Servings) {
        servings = Servings;
    }

    public String getServings() {
        return servings;
    }
}
