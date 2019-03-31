package com.example.baking;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.baking.Items.Recipe;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipeWidgetService;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views;

        views = getListView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getListView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Intent intent = new Intent(context, RecipeWidgetService.class);

        views.setRemoteAdapter(R.id.appwidget_text, intent);

        return views;
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

