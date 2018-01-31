package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.models.Course;
import net.orbit.orbit.models.Teacher;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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

    public void getAllCourses(Context context){
        Log.d("CourseService", "Getting all the courses assigned to current Teacher.");

        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        String UID = securityService.getCurrentUsersUid();
        Teacher teacher = teacherService.getTeacherByUid(UID);

        orbitRestClient.get("get-courses-by-teacher-id/" + UID, null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray courses) {
                Gson gson = new Gson();
                Course[] courseList = gson.fromJson(courses.toString(), Course[].class);
                //activity.updateTeacherList(teacherList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("AddTeacherActivity", "Error when adding new menu_teacher: " + errorResponse);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

}
