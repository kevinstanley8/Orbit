package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.ChooseStudentActivity;
import net.orbit.orbit.activities.EnrollStudentInCourseActivity;
import net.orbit.orbit.activities.HomeActivity;
import net.orbit.orbit.models.pojo.AccountLink;
import net.orbit.orbit.models.dto.AccountLinkDTO;
import net.orbit.orbit.models.dto.EnrollStudentInClassDTO;
import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.models.dto.StudentDTO;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "create-menu_student", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray student) {
                        // called when success happens
                        Log.i("CreateStudentActivity", "Successfully created new menu_teacher: " + student);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("CreateStudentActivity", "Error when creating new menu_student: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }

    public void findStudent(final StudentDTO studentDTO, final String userUID){
        Gson gson = new Gson();
        String json = gson.toJson(studentDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "get-student", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject student) {
                        // called when success happens
                        Log.i("FindStudentActivity", "Successfully found student: " + student);
                        // We have a match student. Need to do linking here.
                        //Toast.makeText(context, "Found a matching student." , Toast.LENGTH_SHORT).show();

                        //convert the json response student to a Student object and get the ID
                        Gson gson = new Gson();
                        Student foundStudent = gson.fromJson(student.toString(), Student.class);

                        AccountLinkDTO accountLinkDTO = new AccountLinkDTO(userUID, foundStudent.getStudentId());
                        linkStudent(accountLinkDTO);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("FindStudentActivity", "Error when finding student: " + errorResponse);
                        Toast.makeText(context, "Error finding student.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }


    public void linkStudent(AccountLinkDTO accountLinkDTO){
        Gson gson = new Gson();
        String json = gson.toJson(accountLinkDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "link-student", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject account) {
                        // called when success happens
                        Gson gson = new Gson();
                        AccountLink accountLink = gson.fromJson(account.toString(), AccountLink.class);

                        Log.i("StudentService", accountLink.getMessage() + account);
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, accountLink.getMessage() , Toast.LENGTH_SHORT).show();
                        context.startActivity(HomeActivity.createIntent(context));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("StudentService", "Error when linking student: " + errorResponse);
                        Toast.makeText(context, "Error linking student, please try again.  If the problem persists contact your administrator", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }


    public void findLinkedStudents(final ChooseStudentActivity activity, String UID)
    {
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.get("find-linked/" + UID, null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray students) {
                // called when success happens
                Gson gson = new Gson();
                List<Student> studentList = gson.fromJson(students.toString(), new TypeToken<List<Student>>(){}.getType());
                activity.updateStudentList(studentList);

                Log.i("StudentService", "Find Linked Student - Successful");
                Toast.makeText(context, "FOUND LINKED STUDENTS" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("StudentService", "Error finding linked students: " + errorResponse);
                Toast.makeText(context, "Error finding linked students", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void findAllStudents(final EnrollStudentInCourseActivity activity)
    {
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        orbitRestClient.get("all-students/", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray students) {
                // called when success happens
                Gson gson = new Gson();
                List<Student> studentList = gson.fromJson(students.toString(), new TypeToken<List<Student>>(){}.getType());
                activity.updateStudentList(studentList);

                Log.i("StudentService", "Find Linked Student - Successful");
                Toast.makeText(context, "FOUND LINKED STUDENTS" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("StudentService", "Error finding linked students: " + errorResponse);
                Toast.makeText(context, "Error finding linked students", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void enrollStudentsInCourse(List<Student> enrollList, int courseID)
    {
        EnrollStudentInClassDTO enrollDTO = new EnrollStudentInClassDTO();

        for(int i = 0; i < enrollList.size(); i++)
        {
            enrollDTO.addEnrollRecord(enrollList.get(i).getStudentId(), courseID);
        }

        Gson gson = new Gson();
        String json = gson.toJson(enrollDTO);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sets the URL for the API url
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        orbitRestClient.post(this.context, "enroll-students-course", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject schedule) {
                        // called when success happens
                        Gson gson = new Gson();

                        //String scheduleMessage = gson.fromJson(schedule.toString(), AccountLink.class);

                        Log.i("StudentService", schedule.toString());
                        // We have a match student. Need to do linking here.
                        Toast.makeText(context, "Enroll Successful" , Toast.LENGTH_SHORT).show();
                        context.startActivity(HomeActivity.createIntent(context));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("StudentService", "Error when linking student: " + errorResponse);
                        Toast.makeText(context, "Error linking student, please try again.  If the problem persists contact your administrator", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });


    }

}
