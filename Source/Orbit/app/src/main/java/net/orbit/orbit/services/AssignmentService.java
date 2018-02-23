package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ViewCourseAssignmentsActivity;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.PropertiesService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Kevin Stanley on 2/06/18.
 */

public class AssignmentService extends BaseService{

    private Context context;

    public AssignmentService(Context context){
        this.context = context;
    }

    public void getAllAssignmentsForCourse(final ViewCourseAssignmentsActivity activity, int courseID){
        Log.d("AssignmentService", "Getting all the assignments for course ID: " + courseID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
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

    public void createAssignment(CreateAssignmentDTO createAssignmentDTO){
        Gson gson = new Gson();
        String json = gson.toJson(createAssignmentDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API urlOrbitRestClient orbitRestClient = new OrbitRestClient(this.context);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
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
    }


}
