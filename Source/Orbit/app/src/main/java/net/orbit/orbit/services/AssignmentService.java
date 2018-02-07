package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ViewCourseAssignmentsActivity;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Kevin Stanley on 2/06/18.
 */

public class AssignmentService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    SecurityService securityService = new SecurityService();
    TeacherService teacherService = new TeacherService();
    Context context;

    public AssignmentService(Context context){
        this.context = context;
    }

    public void getAllAssignmentsForCourse(final ViewCourseAssignmentsActivity activity, int courseID){
        Log.d("AssignmentService", "Getting all the assignments for course ID: " + courseID);
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));

        orbitRestClient.get("all-assignments-for-course/" + courseID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray assignments) {
                Gson gson = new Gson();
                List<Assignment> assignmentList = gson.fromJson(assignments.toString(), new TypeToken<List<Assignment>>(){}.getType());
                activity.updateAssignmentList(assignmentList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("AssignmentService", "Error when getting assignments for course: " + errorResponse);
            }

        });
    }
}
