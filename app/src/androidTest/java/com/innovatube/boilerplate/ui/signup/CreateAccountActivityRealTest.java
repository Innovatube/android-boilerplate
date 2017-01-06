package com.innovatube.boilerplate.ui.signup;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.innovatube.boilerplate.LoadingProgressIdlingResource;
import com.innovatube.boilerplate.R;
import com.innovatube.boilerplate.consts.Consts;
import com.innovatube.boilerplate.ui.base.LoadingDialog;
import com.wdullaer.materialdatetimepicker.date.YearPickerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by quanlt on 28/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityRealTest {

    @Rule
    public ActivityTestRule<CreateAccountActivity> activityActivityTestRule =
            new ActivityTestRule<>(CreateAccountActivity.class);
    private LoadingProgressIdlingResource idlingResource;


    public void testCreateAccountFailAndShowErrorDialog() throws Exception {
        activityActivityTestRule.launchActivity(null);
        onView(withId(R.id.btn_join)).perform(click());
        onView(withText(Consts.GENERIC_ERROR)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountSuccessAndGoToHome() throws Exception {
        onView(withId(R.id.edt_first_name)).perform(typeText("Ethan"));
        onView(withId(R.id.edt_last_name)).perform(typeText("Le"));
        onView(withId(R.id.edt_email_address)).perform(typeText("ethan.le@innovatube.com"));
        onView(withId(R.id.edt_password)).perform(typeText("123456"));
        onView(withId(R.id.edt_confirm_password)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.edt_dob)).perform(click());
        onData(withValue(1993))
                .inAdapterView(isAssignableFrom(YearPickerView.class))
                .perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.edt_promotion_code)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.btn_join)).perform(click());
        onView(withText("Create Account")).inRoot(isDialog()).check(matches(isDisplayed()));

        idlingResource = new LoadingProgressIdlingResource(activityActivityTestRule.getActivity()
                .getSupportFragmentManager(), LoadingDialog.TAG);
        registerIdlingResources(idlingResource);

        onView(withText("Create Account")).inRoot(isDialog()).check(matches(isDisplayed()));
        unregisterIdlingResources(idlingResource);
    }

    public static Matcher<Object> withValue(final int value) {
        return new BoundedMatcher<Object, Integer>(Integer.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + value);
            }

            @Override
            public boolean matchesSafely(Integer item) {
                return value == item;
            }
        };
    }



}
