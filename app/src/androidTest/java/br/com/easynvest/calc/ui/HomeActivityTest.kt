package br.com.easynvest.calc.ui

import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import br.com.easynvest.calc.R
import br.com.easynvest.calc.ui.home.HomeActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    private val monthOfYear: Int = 8
    private val dayOfMonth: Int = 18
    private val year: Int = 2020
    private val amount = "10000"
    private val rate = "123"

    @get:Rule
    var activityScenarioRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    @Test
    fun imcCalculationFlowTest() {
        onView(withId(R.id.inputInvestedAmount)).perform(typeText(amount), pressImeActionButton())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                year,
                monthOfYear,
                dayOfMonth
            )
        )
        onView(withText("OK")).perform(click())
        onView(withId(R.id.inputMaturity)).perform(pressImeActionButton())
        onView(withId(R.id.inputRate)).perform(typeText(rate), pressImeActionButton())
        onView(withId(R.id.buttonSimulate)).perform(click())
    }
}