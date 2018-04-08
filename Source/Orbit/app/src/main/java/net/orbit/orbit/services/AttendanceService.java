package net.orbit.orbit.services;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import net.orbit.orbit.activities.ViewAttendanceActivity;
import net.orbit.orbit.activities.ViewCourseAttendanceActivity;
import net.orbit.orbit.models.dto.SaveAttendanceDTO;
import net.orbit.orbit.models.pojo.Attendance;
import net.orbit.orbit.utils.OrbitRestClient;
public class AttendanceService extends BaseService {
    private Context context;

    public AttendanceService(Context context){
        this.context = context;
    }

    public void getStudentCourseAttendance(final ViewAttendanceActivity activity, final int studentId, int courseId){
        Log.d("Attendance", "Getting all course attendance for student ID " + studentId);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("student-attendance/" + studentId + "/" + courseId, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray attendances) {
                Gson gson = new Gson();
                List<Attendance> attendanceList = gson.fromJson(attendances.toString(), new TypeToken<List<Attendance>>(){}.getType());
                //activity.updateCourseGradeList(attendanceList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("GradeService", "Error when getting course grades for student ID: " + studentId);
            }

        });
    }

    public void getCourseAttendance(final ViewCourseAttendanceActivity activity, final int courseId){
        Log.d("AttendanceService", "Getting all course attendance for course ID " + courseId);
        OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);
        orbitRestClient.get("course-attendance/" + courseId , null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray attendances) {
                Gson gson = new Gson();
                Gson test = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                List<Attendance> attendanceList = new ArrayList<>();
                java.sql.Date date;
                Attendance a;
                for(int i = 0; i < attendances.length(); i ++)
                {
                    String ab = attendances.optString(i);
                    a = test.fromJson(attendances.optString(i), Attendance.class);
                    //Log.d("SugarMonkey", a.toString() + "\n");
                   // Attendance a = (Attendance) attendances.opt(i);
                    attendanceList.add(a);
                }
                //attendanceList = test.fromJson(attendances.toString(), new TypeToken<List<Attendance>>(){}.getType());
                activity.updateAttendanceList(attendanceList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("AttendanceService", "Error when getting course attendance for course ID: " + courseId);
            }

        });
    }

    public void saveCourseAttendance(SaveAttendanceDTO saveAttendanceDTO){
        final OrbitRestClient orbitRestClient = getOrbitRestClient(this.context);

        Gson test = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Gson gson = new Gson();
        String json = test.toJson(saveAttendanceDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Sets the URL for the API url
        orbitRestClient.post(this.context, "save-attendance", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when success happens
                        Log.i("AttendanceService", "Successfully saves attendance.");
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, "Saved attendance successfully" , Toast.LENGTH_SHORT).show();
                        //context.startActivity(ViewCoursesTeacherActivity.createIntent(context));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("AttendanceService", "Error when saving attendance");
                        Toast.makeText(context, "Error saving attendance, please try again.  If the problem persists contact your administrator.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }
}
