package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ViewAssignmentGradesActivity;
import net.orbit.orbit.activities.ViewCourseAssignmentsActivity;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Kevin Stanley on 2/06/18.
 */

public class GradeService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    SecurityService securityService = new SecurityService();
    TeacherService teacherService = new TeacherService();
    Context context;

    public GradeService(Context context){
        this.context = context;
    }

    public void getAllStudentGradesForAssignment(final ViewAssignmentGradesActivity activity, int assignmentID){
        Log.d("GradeService", "Getting all the enrolled students and grades for assignment ID: " + assignmentID);
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));

        orbitRestClient.get("all-grades-for-assignment/" + assignmentID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray grades) {
                Gson gson = new Gson();
                List<Grade> gradeList = gson.fromJson(grades.toString(), new TypeToken<List<Grade>>(){}.getType());
                activity.updateGradeList(gradeList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("AssignmentService", "Error when getting assignments for course: " + errorResponse);
            }

        });
    }

    /*public void createGrade(CreateAssignmentDTO createAssignmentDTO){
        Gson gson = new Gson();
        String json = gson.toJson(createAssignmentDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "create-assignment", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject assignment) {
                        // called when success happens
                        Gson gson = new Gson();
                        Assignment newAssignment = gson.fromJson(assignment.toString(), Assignment.class);

                        Log.i("AccountService", "Created Assignment" + newAssignment.getAssignmentId());
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, "New Assignment: " + newAssignment.getName() + " created!", Toast.LENGTH_SHORT).show();
                        context.startActivity(ViewCourseAssignmentsActivity.createIntent(context, newAssignment.getCourse().getCourseId()));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("StudentService", "Error when linking student: " + errorResponse);
                        Toast.makeText(context, "Error linking student, please try again.  If the problem persists contact your administrator.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }*/


}
