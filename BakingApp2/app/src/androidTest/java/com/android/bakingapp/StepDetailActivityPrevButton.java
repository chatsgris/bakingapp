package com.android.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.android.bakingapp.ui.StepDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mimiliu on 7/13/18.
 */

@RunWith(JUnit4.class)
public class StepDetailActivityPrevButton {
    @Rule
    public ActivityTestRule<StepDetailActivity> stepDetailActivityActivityTestRule = new ActivityTestRule<>(StepDetailActivity.class, true, false);

    @Test
    public void ClickPrevButton() {
        Intent intent = new Intent();
        intent.putExtra("Position", 0);
        intent.putExtra("StepId", 0);
        stepDetailActivityActivityTestRule.launchActivity(intent);

        onView(withId(R.id.prev_button)).perform(click());
        onView(withId(R.id.step_instruction_value)).check(matches(withText("6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!")));
    }
}
