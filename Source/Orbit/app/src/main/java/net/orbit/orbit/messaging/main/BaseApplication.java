package net.orbit.orbit.messaging.main;

import android.app.Application;

import com.sendbird.android.SendBird;
import net.orbit.orbit.utils.OrbitUserPreferences;
public class BaseApplication extends Application {

    private static final String APP_ID = "FE7B3711-E010-41D3-9521-668C346F3F46";
    public static final String VERSION = "3.0.39";


    @Override
    public void onCreate() {
        super.onCreate();
        OrbitUserPreferences.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }
}
