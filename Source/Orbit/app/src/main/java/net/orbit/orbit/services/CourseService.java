package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import net.orbit.orbit.activities.ViewCoursesActivity;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.models.exceptions.ErrorResponse;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.ServerCallback;

/**
 * Created by brocktubre on 1/29/18.
 */

public class CourseService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    SecurityService securityService = new SecurityService();
    TeacherService teacherService = new TeacherService();
    Context context;

    public CourseService(Context context){
        this.context = context;
    }

    public void getAllCourses(final ViewCoursesActivity activity){
        Log.d("CourseService", "Getting all the courses assigned to current Teacher.");
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));

        String UID = securityService.getCurrentUsersUid();
        teacherService.getTeacherByUid(UID, activity, new ServerCallback<Teacher>() {
            @Override
            public void onSuccess(Teacher result) {
                Log.i("ViewCoursesActivity", "Found teacher and call back is working: " + result);
            }

            @Override
            public void onFail(ErrorResponse errorResponse) {
                Log.i("ViewCoursesActivity", "Error finding teacher and call back is working: " + errorResponse.getMessage());
            }
        });
    }

}
