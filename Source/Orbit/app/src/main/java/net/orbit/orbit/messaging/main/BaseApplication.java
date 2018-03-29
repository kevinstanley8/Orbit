package net.orbit.orbit.messaging.main;

import android.app.Application;

import com.sendbird.android.SendBird;

import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitUserPreferences;
import net.orbit.orbit.utils.PropertiesService;

public class BaseApplication extends Application {

    //private static final String APP_ID = Constants.SENDBIRD_APPID;
    public static final String VERSION = "3.0.39";


    @Override
    public void onCreate() {
        super.onCreate();
        OrbitUserPreferences.init(getApplicationContext());


        PropertiesService propertiesService = new PropertiesService(this);
        String APP_ID= propertiesService.getProperty(this, Constants.SENDBIRD_APPID);
        SendBird.init(APP_ID, getApplicationContext());
    }
}
