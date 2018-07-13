package com.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import com.android.bakingapp.ui.StepDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.android.bakingapp.TestUtils.ViewActionUtils.atPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by mimiliu on 7/13/18.
 */

@RunWith(JUnit4.class)
public class StepDetailActivityNextButtonTest {

    @Rule
    public ActivityTestRule<StepDetailActivity> stepDetailActivityActivityTestRule = new ActivityTestRule<>(StepDetailActivity.class, true, false);

    @Test
    public void ClickNextButton() {
        Intent intent = new Intent();
        intent.putExtra("Position", 0);
        intent.putExtra("StepId", 0);
        stepDetailActivityActivityTestRule.launchActivity(intent);

        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.step_instruction_value)).check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));
    }
}
