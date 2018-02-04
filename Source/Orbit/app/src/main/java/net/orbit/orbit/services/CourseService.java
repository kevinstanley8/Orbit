package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ChooseCourseActivity;
import net.orbit.orbit.activities.HomeActivity;
import net.orbit.orbit.activities.ViewCoursesTeacherActivity;
import net.orbit.orbit.models.dto.AssignCourseToTeacherDTO;
import net.orbit.orbit.models.pojo.Course;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.models.exceptions.ErrorResponse;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.OrbitUserPreferences;
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

    public void getAllCoursesAssignedToCurrentTeacher(final ViewCoursesTeacherActivity activity){
        Log.d("CourseService", "Getting all the courses assigned to current Teacher.");
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));

        String UID = securityService.getCurrentUsersUid();
        teacherService.getTeacherByUid(UID, activity, new ServerCallback<Teacher>() {
            @Override
            public void onSuccess(Teacher teacher) {
                Log.i("CourseService", "Found teacher and call back is working: " + teacher);
                OrbitUserPreferences orbitPref = new OrbitUserPreferences(context);
                orbitPref.storeUserPreference("loggedInTeacher", teacher);
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
                Log.i("CourseService", "Error finding teacher and call back is working: " + errorResponse.getMessage());

            }
        });
    }

    public void getAllCourses(final ChooseCourseActivity activity){
        Log.d("CourseService", "Getting all the courses.");
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));

        orbitRestClient.get("all-courses/", null, new JsonHttpResponseHandler(){

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

    public void assignCourseToTeacher(List<Course> courseList){
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(context);
        Teacher teacher = orbitPref.getTeacherPreferenceObj("loggedInTeacher");
        AssignCourseToTeacherDTO assignDTO = new AssignCourseToTeacherDTO();
        for(Course c : courseList){
            assignDTO.addCourseRecord(c);
        }

        Gson gson = new Gson();
        String json = gson.toJson(assignDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "assign-course-to-teacher/" + teacher.getTeacherID(), entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        // called when success happens
                        Log.i("CourseService", "Successfully assigned courses to teacher.");
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, "Assign courses successfully" , Toast.LENGTH_SHORT).show();
                        context.startActivity(HomeActivity.createIntent(context));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("CourseService", "Error when assigning courses to teacher: " + errorResponse);
                        Toast.makeText(context, "Error assigning courses to teacher, please try again.  If the problem persists contact your administrator.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }

}
