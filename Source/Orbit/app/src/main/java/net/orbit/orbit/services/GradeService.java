package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.orbit.orbit.activities.CourseGradesActivity;
import net.orbit.orbit.activities.ViewAssignmentGradesActivity;
import net.orbit.orbit.activities.ViewAssignmentGradesStudentActivity;
import net.orbit.orbit.activities.ViewCourseAssignmentsActivity;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.CourseGrade;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.models.pojo.User;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.OrbitUserPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Kevin Stanley on 2/06/18.
 */

public class GradeService extends  BaseService{
    private Context context;

    public GradeService(Context context){
        this.context = context;
    }

    public void getAllStudentGradesForAssignment(final ViewAssignmentGradesActivity activity, final GetGradesForAssignmentDTO getGradesForAssignmentDTO){
        Gson gson = new Gson();
        String json = gson.toJson(getGradesForAssignmentDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("GradeService", "Getting all the enrolled students and grades for course ID " + getGradesForAssignmentDTO.getCourseID() + " assignment ID: " + getGradesForAssignmentDTO.getAssignmentID());
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("all-grades-for-assignment/" + getGradesForAssignmentDTO.getCourseID() + "/" + getGradesForAssignmentDTO.getAssignmentID(), new RequestParams("getGradesForAssignmentDTO", entity), new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray grades) {
                Gson gson = new Gson();
                List<Grade> gradeList = gson.fromJson(grades.toString(), new TypeToken<List<Grade>>(){}.getType());
                activity.updateGradeList(gradeList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("GradeService", "Error when getting grades for assignment: " + getGradesForAssignmentDTO.getAssignmentID());
            }

        });
    }

    public void saveGrades(SaveGradesDTO saveGradesDTO){
        final OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);

        Gson gson = new Gson();
        String json = gson.toJson(saveGradesDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Sets the URL for the API url
        orbitRestClient.post(this.context, "save-grades", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when success happens
                        Log.i("GradeService", "Successfully saves grades.");
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, "Saved grades successfully" , Toast.LENGTH_SHORT).show();
                        //context.startActivity(ViewCoursesTeacherActivity.createIntent(context));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("GradeService", "Error when saving grades");
                        Toast.makeText(context, "Error saving grades, please try again.  If the problem persists contact your administrator.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }


    public void getCourseGrades(final CourseGradesActivity activity, final int studentID){

        Log.d("GradeService", "Getting all course grades for student ID " + studentID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("course-grades/" + studentID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray grades) {
                Gson gson = new Gson();
                List<CourseGrade> gradeList = gson.fromJson(grades.toString(), new TypeToken<List<CourseGrade>>(){}.getType());
                activity.updateCourseGradeList(gradeList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("GradeService", "Error when getting course grades for student ID: " + studentID);
            }

        });
    }

    public void getStudentCourseGrades(final ViewAssignmentGradesStudentActivity activity, final int studentID, final int courseID){

        //need to figure out how to find student ID based on logged in user.
        //student logins will come from account link table
        //parent logins will have to choose which student they want to see

        Log.d("GradeService", "Getting all grades for student ID " + studentID + " courseID " + courseID);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("student-grades/" + studentID + "/" + courseID, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray grades) {
                Gson gson = new Gson();
                List<Grade> gradeList = gson.fromJson(grades.toString(), new TypeToken<List<Grade>>(){}.getType());
                activity.updateGradeList(gradeList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("GradeService", "Error when getting course grades for student ID: " + studentID + " courseID");
            }

        });
    }


}
