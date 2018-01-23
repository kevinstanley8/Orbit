package net.orbit.orbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.orbit.orbit.models.User;

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

    public void storeUserPreference(String prefName, User user)
    {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(prefName, json);
        editor.commit();
    }

    public String getUserPreference(String prefName)
    {
        String prefValue = pref.getString(prefName, null);
        return prefValue;
    }

    public User getUserPreferenceObj(String prefName)
    {
        Gson gson = new Gson();
        String json = pref.getString(prefName, "");
        User user = gson.fromJson(json, User.class);
        if (user != null) {
            return user;
        }
        return null;
    }

}
