package net.orbit.orbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin Stanley on 11/7/2017.
 */

public class OrbitUserPreferences {
    private Context context;
    final private String prefName = "Orbit";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public OrbitUserPreferences(Context context)
    {
        this.context = context;
        pref = this.context.getSharedPreferences(prefName, 0);
        editor = pref.edit();
    }

    public void storeUserPreference(String prefName, String prefValue)
    {
        editor.putString(prefName, prefValue); // Storing long
        editor.commit();
    }

    public String getUserPreference(String prefName)
    {
        String prefValue = pref.getString(prefName, null);
        return prefValue;
    }

}
