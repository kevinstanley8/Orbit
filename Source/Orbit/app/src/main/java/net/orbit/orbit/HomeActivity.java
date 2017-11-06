package net.orbit.orbit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import net.orbit.orbit.Service.PropertiesService;
import net.orbit.orbit.Utils.OrbitRestClient;
import net.orbit.orbit.Model.Teacher;
import net.orbit.orbit.Utils.PropertyReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class HomeActivity extends AppCompatActivity {
    PropertiesService propertiesService = new PropertiesService();
    OrbitRestClient orbitRestClient = new OrbitRestClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Sets the URL for the API url
        String apiUrl = propertiesService.getProperty(this,"orbit.api.url");
        orbitRestClient.setBaseUrl(apiUrl);

        final TextView textView = (TextView)findViewById(R.id.students);
        final Button button = (Button) findViewById(R.id.addTeacher);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("HomeActivity", "Add a new Test Teacher.");

                // Creates a new teacher Object to send to API
                Teacher newTeacher = new Teacher("Mike", "Oneal", "09/28/1990","123456789", "2610 Bardot Ln", null, "Bossier", "LA", "71111");
                Gson gson = new Gson();
                String json = gson.toJson(newTeacher);
                StringEntity entity = null;
                try {
                    entity = new StringEntity(json.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                orbitRestClient.post(HomeActivity.this, "add-teacher", entity, "application/json",
                        new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray teacher) {
                        // called when success happens
                        Log.i("HomeActivity", "Successfully added new teacher: " + teacher);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("HomeActivity", "Error when adding new teacher: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });

            }
        });

        orbitRestClient.get("all-students", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray students) {
                Log.i("HomeActivity", "Successfully received JSONArray with all students!");
                try {
                    JSONObject student1 = students.getJSONObject(0);
                    textView.append("ID: " + student1.getString("studentId") + " Name: " + student1.getString("studentFirstName") + " " + student1.getString("studentLastName")  + "\n");
                    JSONObject student2 = students.getJSONObject(1);
                    textView.append("ID: " + student2.getString("studentId") + " Name: " + student2.getString("studentFirstName") + " " + student2.getString("studentLastName") );
                } catch (JSONException e) {
                    e.getStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("HomeActivity", "Error code: " + statusCode);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });

    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        return i;
    }

}
