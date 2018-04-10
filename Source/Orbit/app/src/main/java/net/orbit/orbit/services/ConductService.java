package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.orbit.orbit.activities.ConductActivity;
import net.orbit.orbit.activities.CourseGradesActivity;
import net.orbit.orbit.activities.ViewAssignmentGradesActivity;
import net.orbit.orbit.activities.ViewAssignmentGradesStudentActivity;
import net.orbit.orbit.activities.ViewMyConductActivity;
import net.orbit.orbit.activities.ViewMyConductCourseActivity;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
import net.orbit.orbit.models.dto.SaveConductDTO;
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Conduct;
import net.orbit.orbit.models.pojo.CourseGrade;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Kevin Stanley on 2/06/18.
 */

public class ConductService extends  BaseService{
    private Context context;

    public ConductService(Context context){
        this.context = context;
    }

    public void saveConduct(SaveConductDTO saveConductDTO){
        final OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = gson.toJson(saveConductDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Sets the URL for the API url
        orbitRestClient.post(this.context, "save-conduct", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when success happens
                        Log.i("ConductService", "Successfully saves conduct.");
                        Toast.makeText(context, "Saved conduct successfully" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("ConductService", "Error when saving conduct");
                        Toast.makeText(context, "Error saving conduct, please try again.  If the problem persists contact your administrator.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }


    public void getCourseConduct(final ConductActivity activity, final int courseID){

        Log.d("ConductService", "Getting all conduct records for course ID " + courseID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("course-conduct/" + courseID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray conducts) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<Conduct> conductList = gson.fromJson(conducts.toString(), new TypeToken<List<Conduct>>(){}.getType());
                activity.updateConductList(conductList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("ConductService", "Error when getting course conduct for course ID: " + courseID);
            }

        });
    }

    public void getStudentConduct(final ViewMyConductActivity activity, final int studentID){
        Log.d("ConductService", "Getting all conduct for student ID " + studentID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("student-conduct/" + studentID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray conducts) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<Conduct> conductList = gson.fromJson(conducts.toString(), new TypeToken<List<Conduct>>(){}.getType());
                activity.updateConductList(conductList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("ConductService", "Error when getting student conduct for student ID: " + studentID);
            }

        });
    }

    public void getStudentDailyConduct(final ViewMyConductCourseActivity activity, final int studentID, final int courseID){
        Log.d("ConductService", "Getting all conduct for student ID " + studentID + " course ID " + courseID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("student-daily-conduct/" + studentID + "/" + courseID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray conducts) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                List<Conduct> conductList = gson.fromJson(conducts.toString(), new TypeToken<List<Conduct>>(){}.getType());
                activity.updateConductList(conductList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("ConductService", "Error when getting daily student conduct for student ID: " + studentID + " course ID " + courseID);
            }

        });
    }


}
