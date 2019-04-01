package com.example.baking.Tablet_Tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baking.MainActivity;
import com.example.baking.R;
import com.example.baking.Utils.RecipesAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class TabletTests {

    public static final String First_Recipe_Step = "3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.";
    public static final String Second_Recipe_Step = "2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.";
    public static final String Third_Recipe_Step = "2. Combine the cake flour, 400 grams (2 cups) of sugar, baking powder, and 1 teaspoon of salt in the bowl of a stand mixer. " +
            "Using the paddle attachment, beat at low speed until the dry ingredients are mixed together, about one minute";
    public static final String Fourth_Recipe_Step = "2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. " +
            "Stir in the melted butter and 1 teaspoon of vanilla extract until uniform. ";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkFirst() {
        onView(ViewMatchers.withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_RecyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.fragment_ingredients)).check(matches(withText(First_Recipe_Step)));

    }

    @Test
    public void checkSecond() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recipe_RecyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.fragment_ingredients)).check(matches(withText(Second_Recipe_Step)));
    }

    @Test
    public void checkThird() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.recipe_RecyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.fragment_ingredients)).check(matches(withText(Third_Recipe_Step)));
    }

    @Test
    public void checkFourth() {
        onView(withId(R.id.recipe_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recipe_RecyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.fragment_ingredients)).check(matches(withText(Fourth_Recipe_Step)));
    }
}
