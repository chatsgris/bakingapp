package com.android.bakingapp.utils;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;
import com.android.bakingapp.ui.MainActivity;


public class RecipeWidgetProvider extends AppWidgetProvider {
    static int mPosition = 0;
    static String WIDGET_BUTTON = "WIDGET_BUTTON_CLICKED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, RecipeWidgetService.class);
            intent.putExtra("Position", mPosition);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            views.setTextViewText(R.id.widget_title, new Recipes().getRecipeTitle(mPosition));

            views.setRemoteAdapter(appWidgetId, R.id.widget_lv, intent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPendingIntent = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_lv, clickPendingIntent);

            Intent clickButtonIntent = new Intent(context, getClass());
            clickButtonIntent.setAction(WIDGET_BUTTON);
            PendingIntent clickButtonPendingIntent = PendingIntent.getBroadcast(context, 0, clickButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_next_button, clickButtonPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            int total_recipes = new Recipes().getRecipes().length();
            if (mPosition < (total_recipes - 1)) {
                mPosition += 1;
            } else {
                mPosition = 0;
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), getClass().getName())));
        }
    }
}

