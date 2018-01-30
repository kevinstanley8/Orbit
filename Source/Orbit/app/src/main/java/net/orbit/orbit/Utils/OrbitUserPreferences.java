package net.orbit.orbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.orbit.orbit.activities.EnrollStudentInCourseActivity;
import net.orbit.orbit.models.Student;
import net.orbit.orbit.models.User;

import java.util.List;

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

    /**
     * Storing text in shared preferences
     * @param prefName
     * @param prefValue
     */
    public void storeUserPreference(String prefName, String prefValue)
    {
        editor.putString(prefName, prefValue); // Storing long
        editor.commit();
    }

    /**
     * Storing object in shared preferences
     * @param prefName
     * @param user
     */
    public void storeUserPreference(String prefName, User user)
    {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(prefName, json);
        editor.commit();
    }

    /**
     * Store student list
     * @param prefName
     * @param students
     */
    public void storeUserPreference(String prefName, List<Student> students)
    {
        String json = new Gson().toJson(students, new TypeToken<List<Student>>(){}.getType());
        editor.putString(prefName, json);
        editor.commit();
    }

    /**
     * Getting text from shared preferences
     * @param prefName
     * @return
     */
    public String getUserPreference(String prefName)
    {
        String prefValue = pref.getString(prefName, null);
        return prefValue;
    }

    /**
     * Getting object from user preferences
     * @param prefName
     * @return
     */
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

    /**
     * Clear shared preferences
     */
    public void clear(){
        editor.clear();
        editor.commit();
    }

}
