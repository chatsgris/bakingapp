package com.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.android.bakingapp.TestUtils.ViewActionUtils;
import com.android.bakingapp.ui.RecipeDetailActivity;
import com.android.bakingapp.ui.StepDetailActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by mimiliu on 7/12/18.
 */

public class RecipeDetailIntentsTest {

    @Rule
    public IntentsTestRule<RecipeDetailActivity> recipeDetailActivityIntentsTestRule = new IntentsTestRule<>(RecipeDetailActivity.class, true, false);

    @Test
    public void clickStepShortDescription() {
        Intent intent = new Intent();
        intent.putExtra("Position", 0);
        recipeDetailActivityIntentsTestRule.launchActivity(intent);

        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        onView(withId(R.id.steps_rv_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActionUtils.clickChildViewWithId(R.id.short_description)));
        intended(allOf(
                IntentMatchers.hasExtraWithKey("Position"),
                IntentMatchers.hasExtraWithKey("StepId")
        ));
        intended(hasComponent(StepDetailActivity.class.getName()));
    }
}
