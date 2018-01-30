package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import net.orbit.orbit.utils.OrbitRestClient;

/**
 * Created by brocktubre on 1/29/18.
 */

public class CourseService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    Context context;

    public CourseService(Context context){
        this.context = context;
    }

    public void getAllCourses(Context context){
        Log.d("CourseService", "Getting all the courses assigned to current Teacher.");
    }

}
