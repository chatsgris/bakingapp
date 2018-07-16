package com.android.bakingapp.utils;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by mimiliu on 7/15/18.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RecipeViewsFactory(this.getApplicationContext(),
                intent));
    }
}
