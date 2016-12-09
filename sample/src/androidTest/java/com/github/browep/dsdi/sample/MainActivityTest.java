package com.github.browep.dsdi.sample;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.TextView;

/**
 * Test the main activity with the TestDependencySupplier
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void response() throws Exception {
        TextView textView = (TextView) mActivityRule.getActivity().findViewById(R.id.text);
        String actualText = textView.getText().toString();
        Assert.assertEquals("Test Repo1\n" +
                "Test Repo2\n" +
                "Test Repo3\n", actualText);
    }
}
