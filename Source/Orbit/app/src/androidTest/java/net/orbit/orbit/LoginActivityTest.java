package net.orbit.orbit;

import android.support.test.rule.ActivityTestRule;

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

    private HomeActivity mLoginActivity = null;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLaunch(){

    }

    @After
    public void tearDown() throws Exception {
    }

}