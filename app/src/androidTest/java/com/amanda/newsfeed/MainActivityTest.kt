package com.amanda.newsfeed


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amanda.newsfeed.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    @Throws
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun initialSelectionOfSpinnerIsALL() {
        onView(withText("ALL"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun spinnerIsClickable() {
        onView(withId(R.id.news_type_spinner)).perform(click())
    }
}