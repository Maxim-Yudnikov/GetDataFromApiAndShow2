package com.maxim.getdatafromapiandshow2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maxim.getdatafromapiandshow2.Mather.atPosition
import com.maxim.getdatafromapiandshow2.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_get_item() {
        onView(withId(R.id.textView)).check(matches(withText("Click button to get a random fact")))
        onView(withText("Get fact")).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("item1")))
        onView(withText("Get fact")).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("item2")))
    }

    @Test
    fun test_get_item_and_save() {
        onView(withId(R.id.textView)).check(matches(withText("Click button to get a random fact")))
        onView(withText("Save fact")).check(matches(isNotEnabled()))
        onView(withText("Get fact")).perform(click())
        onView(withText("Save fact")).check(matches(isEnabled()))
        onView(withId(R.id.savedDataText)).check(matches(withText("No saved data")))

        onView(withText("Save fact")).perform(click())
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("item1"))
                )
            )
        )

        onView(withText("Get fact")).perform(click())
        onView(withText("Save fact")).perform(click())
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("item2"))
                )
            )
        )
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    1,
                    hasDescendant(withText("item1"))
                )
            )
        )
    }

    @Test
    fun test_get_item_and_save_and_remove() {
        onView(withId(R.id.textView)).check(matches(withText("Click button to get a random fact")))
        onView(withText("Save fact")).check(matches(isNotEnabled()))
        onView(withText("Get fact")).perform(click())
        onView(withText("Save fact")).check(matches(isEnabled()))
        onView(withId(R.id.savedDataText)).check(matches(withText("No saved data")))

        onView(withText("Save fact")).perform(click())
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("item1"))
                )
            )
        )

        onView(withId(R.id.deleteButton)).perform(click())
        onView(withId(R.id.recyclerView)).check(
            matches(
                atPosition(
                    0,
                    withText("No saved data")
                )
            )
        )
    }
}