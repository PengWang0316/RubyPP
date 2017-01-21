package com.example.pengwang.rubypp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import android.app.Dialog;
import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.pengwang.rubypp.Fragments.AddRecordDialogFragment;
import com.example.pengwang.rubypp.Fragments.InputNameDialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Peng on 1/20/2017.
 * For Main activity instrument test
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityInstrumentTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {

//        onView(withId(R.id.main_tool_bar)).check(matches(isDisplayed()));
        DialogFragment inputNameDialog = ((DialogFragment) mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentByTag(InputNameDialog.INPUT_NAME_DIALOG));
        if (inputNameDialog != null) {
//            onView(withId(R.id.input_name_dialog_name_view)).perform(typeText("Peng"));
            inputNameDialog.getDialog().cancel();
            Log.d("MainActivityTest", "-----------------Type Name------------------");
        }


        Log.d("MainActivityTest", "-----------------End Test------------------");
        onView(withId(R.id.main_tool_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.main_floating_action_button)).check(matches(isDisplayed()));
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.main_action_menu_refresh)).check(matches(isDisplayed()));

//        clink add button and dialog shows up
        DialogFragment addContentDialogFragment = ((DialogFragment) mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentByTag(AddRecordDialogFragment.INSERT_DIALOG_TAG));
        assertNull(addContentDialogFragment);
        Thread.sleep(2000);
        onView(withId(R.id.main_floating_action_button)).perform(click());

        addContentDialogFragment = ((DialogFragment) mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().findFragmentByTag(AddRecordDialogFragment.INSERT_DIALOG_TAG));
        assertNotNull(addContentDialogFragment);


//        onView(withId(R.id.main_floating_action_button)).perform(click());
    }


}