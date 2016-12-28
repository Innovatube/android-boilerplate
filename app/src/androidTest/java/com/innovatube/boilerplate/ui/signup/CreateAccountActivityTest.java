package com.innovatube.boilerplate.ui.signup;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.innovatube.boilerplate.EspressoDaggerMockRule;
import com.innovatube.boilerplate.R;
import com.innovatube.boilerplate.consts.Consts;
import com.innovatube.boilerplate.data.InnovatubeRepository;
import com.innovatube.boilerplate.data.model.UserId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.concurrent.TimeUnit;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by quanlt on 28/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest {

    @Rule
    public EspressoDaggerMockRule daggerMockRule = new EspressoDaggerMockRule();

    @Rule
    public ActivityTestRule<CreateAccountActivity> activityActivityTestRule =
            new ActivityTestRule<>(CreateAccountActivity.class, true, false);

    @Mock
    InnovatubeRepository mMockInnovatubeRepository;

    @Test
    public void testCreateAccountFailAndShowErrorDialog() throws Exception {
        when(mMockInnovatubeRepository.createAccount(anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.<UserId>error(new Exception("")));
        activityActivityTestRule.launchActivity(null);
        onView(withId(R.id.btn_join)).perform(click());
        onView(withText(Consts.GENERIC_ERROR)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountSuccessAndGoToHome() throws Exception {
        UserId userId = new UserId();
        userId.setUserId(1);
        when(mMockInnovatubeRepository.createAccount(anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(userId).delay(500, TimeUnit.MILLISECONDS));
        activityActivityTestRule.launchActivity(null);
        onView(withId(R.id.edt_first_name)).perform(typeText("Ethan"));
        onView(withId(R.id.edt_last_name)).perform(typeText("Le"));
        onView(withId(R.id.edt_email_address)).perform(typeText("ethan.le@innovatube.com"));
        onView(withId(R.id.edt_password)).perform(typeText("123456"));
        onView(withId(R.id.edt_confirm_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.edt_dob)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.edt_promotion_code)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_join)).perform(click());
        onView(withText("Create Account")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Hello World!")).check(matches(isDisplayed()));
    }
}
