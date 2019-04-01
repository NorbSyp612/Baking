package com.example.baking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baking.Utils.RecipesAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String First_Recipe = "Brownies";
    private Context mContext;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mContext = InstrumentationRegistry.getContext();
    }


    @Test
    public void checkFirst() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        if (mContext.getResources().getBoolean(R.bool.Tablet_Check)) {
            onView(withId(R.id.recipe_RecyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        }
    }

    @Test
    public void checkSecond() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void checkThird() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }

    @Test
    public void checkFourth() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }
}
