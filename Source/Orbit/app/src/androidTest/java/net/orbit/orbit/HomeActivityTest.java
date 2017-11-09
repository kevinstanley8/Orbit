package net.orbit.orbit;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.orbit.orbit.activities.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brocktubre on 11/7/17.
 */
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityTestActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    private HomeActivity mHomeActivity = null;

    @Before
    public void setUp() throws Exception {
        mHomeActivity = mHomeActivityTestActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mHomeActivity.findViewById(R.id.homeBanner);
        assertNotNull(view);
    }


    @After
    public void tearDown() throws Exception {
        mHomeActivity = null;
    }

}