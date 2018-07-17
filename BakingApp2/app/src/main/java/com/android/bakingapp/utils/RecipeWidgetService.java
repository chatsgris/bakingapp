package com.android.bakingapp.utils;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RecipeViewsFactory(this.getApplicationContext(),
                intent));
    }
}
