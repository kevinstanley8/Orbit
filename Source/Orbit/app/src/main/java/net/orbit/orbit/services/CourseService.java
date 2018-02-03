package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ViewCoursesActivity;
import net.orbit.orbit.models.pojo.Course;
import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.models.exceptions.ErrorResponse;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.ServerCallback;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

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
            public void onSuccess(Teacher teacher) {
                Log.i("ViewCoursesActivity", "Found teacher and call back is working: " + teacher);
                orbitRestClient.get("get-courses-by-teacher-id/" + teacher.getTeacherID(), null, new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray courses) {
                        Gson gson = new Gson();
                        List<Course> courseList = gson.fromJson(courses.toString(), new TypeToken<List<Course>>(){}.getType());
                        activity.updateCourseList(courseList);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("CourseService", "Error when adding new menu_teacher: " + errorResponse);
                    }

                });
            }

            @Override
            public void onFail(ErrorResponse errorResponse) {
                Log.i("ViewCoursesActivity", "Error finding teacher and call back is working: " + errorResponse.getMessage());
            }
        });
    }

}
