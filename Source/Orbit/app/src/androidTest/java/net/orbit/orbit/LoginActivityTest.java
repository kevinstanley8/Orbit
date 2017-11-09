package net.orbit.orbit;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.orbit.orbit.activities.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brocktubre on 11/7/17.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity mLoginActivity = null;
    @Before
    public void setUp() throws Exception {
        mLoginActivity = mLoginActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mLoginActivity.findViewById(R.id.login_form);
        assertNotNull(view);

    }

    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
    }

}