package com.example.baking.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.R;

import java.util.ArrayList;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<Recipe> mRecipes;
    ArrayList<RecipeSteps> mRecipeSteps;


    public RecipeRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        JsonParser jsonParser = new JsonParser(mContext);
        mRecipes = jsonParser.parseJson();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int lastClicked = sharedPreferences.getInt((mContext.getResources().getString(R.string.PREF_KEY_CLICKED)), 21);

        if (lastClicked != 21) {
            mRecipeSteps = mRecipes.get(lastClicked).getRecipeSteps();
        } else {
            mRecipeSteps = mRecipes.get(0).getRecipeSteps();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipeSteps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String recipeStep = mRecipeSteps.get(position).getShortDescription();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_item);
        views.setTextViewText(R.id.widget_recipe_item, recipeStep);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
