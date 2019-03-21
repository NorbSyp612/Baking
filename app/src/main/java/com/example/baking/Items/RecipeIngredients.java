package com.example.baking.Items;

public class RecipeIngredients {

    private String quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredients() { }

    public void setQuantity(String quantityFromJson) {
        quantity = quantityFromJson;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setMeasure(String measureFromJson) {
        measure = measureFromJson;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredientFromJson) {
        ingredient = ingredientFromJson;
    }

    public String getIngredient() {
        return ingredient;
    }
}
