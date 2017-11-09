package net.orbit.orbit.Service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.Model.Student;
import net.orbit.orbit.Utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by David on 11/8/2017.
 */

public class StudentService
{
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    Context context;

    public StudentService(Context context){
        this.context = context;
    }
    public void addStudent(Student newStudent){
        Gson gson = new Gson();
        String json = gson.toJson(newStudent);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        orbitRestClient.post(this.context, "create-student", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray student) {
                        // called when success happens
                        Log.i("CreateStudentActivity", "Successfully created new teacher: " + student);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("CreateStudentActivity", "Error when creating new student: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }
}
