package com.android.bakingapp;

import android.app.Activity;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.android.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by mimiliu on 7/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListFragmentTest {

    @Rule
    public IntentsTestRule<MainActivity> mIntentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem

}
