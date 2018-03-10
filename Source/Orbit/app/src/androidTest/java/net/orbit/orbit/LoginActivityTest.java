package net.orbit.orbit;

import android.os.Looper;

import net.orbit.orbit.activities.LoginActivity;

import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kevin Stanley on 3/9/2018.
 */

public class LoginActivityTest {

    @Test
    public void loginActivity_TestEmailAndPasswords() {
        Looper.prepare();
        LoginActivity loginActivity = new LoginActivity();

        //test for valid username and password
        assertTrue(loginActivity.isEmailValid("orbit@testemail.com"));
        assertTrue(loginActivity.isPasswordValid("Password123"));

        //test for invalid username and password
        assertFalse(loginActivity.isEmailValid("orbit.com"));
        assertFalse(loginActivity.isPasswordValid("test"));
    }
}
