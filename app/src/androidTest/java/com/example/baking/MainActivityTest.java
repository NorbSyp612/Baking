package com.example.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baking.Utils.RecipesAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String First_Recipe = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkFirst() {
     //   onData(anything()).inAdapterView(withId(R.id.recipe_RecyclerView)).atPosition(1).perform(click());
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
     //   onData(anything()).inAdapterView(withId(R.id.recipe_RecyclerView2)).atPosition(1).perform(click());
    }
}
