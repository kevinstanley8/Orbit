package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.orbit.orbit.activities.ViewAssignmentGradesActivity;
import net.orbit.orbit.activities.ViewCourseAssignmentsActivity;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
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


}
