package com.android.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import com.android.bakingapp.TestUtils.ViewActionUtils;
import com.android.bakingapp.ui.MainActivity;
import com.android.bakingapp.ui.RecipeDetailActivity;
import com.android.bakingapp.ui.RecipeDetailFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.android.bakingapp.TestUtils.ViewActionUtils.atPosition;

/**
 * Created by mimiliu on 7/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItems() {
        onView(withId(R.id.recipes_rv_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActionUtils.clickChildViewWithId(R.id.recipe_item)));
        onView(withId(R.id.ingredient_header)).check(matches(withText("Ingredients")));
        onView(withId(R.id.ingredients_rv_view)).check(matches(atPosition(0, withText("Graham Cracker crumbs"))));
    }
}
