package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.AllTeachersActivity;
import net.orbit.orbit.activities.ViewCoursesActivity;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.models.exceptions.ErrorResponse;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.ServerCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by brocktubre on 11/6/17.
 */

public class TeacherService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    Context context;

    public TeacherService(Context context){
        this.context = context;
    }

    public TeacherService() { }

    public void addTeacher(Teacher newTeacher){
        Gson gson = new Gson();
        String json = gson.toJson(newTeacher);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "add-menu_teacher", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray teacher) {
                        // called when success happens
                        Log.i("TeacherService", "Successfully added new menu_teacher: " + teacher);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("TeacherService", "Error when adding new menu_teacher: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }

    public void viewTeachers(final AllTeachersActivity activity){
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.get("all-teachers", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray teachers) {
                Gson gson = new Gson();
                Teacher[] teacherList = gson.fromJson(teachers.toString(), Teacher[].class);
                activity.updateTeacherList(teacherList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("TeacherService", "Error when adding new menu_teacher: " + errorResponse);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });

    }

    public void getTeacherByUid(String UID, final ViewCoursesActivity activity , final ServerCallback<Teacher> callback){
        orbitRestClient.setBaseUrl(propertiesService.getProperty(activity, Constants.ORBIT_API_URL));
        orbitRestClient.get("get-teaher-by-uid/" + UID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonTeacher) {
                Gson gson = new Gson();
                Log.i("TeacherService", "Successfully found a teacher: " + jsonTeacher);
                Teacher teacher = gson.fromJson(jsonTeacher.toString(), Teacher.class);
                callback.onSuccess(teacher);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("TeacherService", "Error when finding teacher by UID: " + errorResponse);
                Gson gson = new Gson();
                ErrorResponse er = gson.fromJson(errorResponse.toString(), ErrorResponse.class);
                callback.onFail(er);
            }

        });
    }

}
