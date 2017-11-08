package net.orbit.orbit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.Service.PropertiesService;
import net.orbit.orbit.Utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends BaseActivity {
    PropertiesService propertiesService = new PropertiesService();
    OrbitRestClient orbitRestClient = new OrbitRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_home, relativeLayout);

        // Sets the URL for the API url
        String apiUrl = propertiesService.getProperty(this,"orbit.api.url");
        orbitRestClient.setBaseUrl(apiUrl);

        final TextView textView = (TextView)findViewById(R.id.students);
        final Button button = (Button) findViewById(R.id.addTeacher);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, AddTeacherActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        getStudents();

    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        return i;
    }

    public void getStudents(){
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
                    //textView.append("ID: " + student1.getString("studentId") + " Name: " + student1.getString("studentFirstName") + " " + student1.getString("studentLastName")  + "\n");
                    JSONObject student2 = students.getJSONObject(1);
                    //textView.append("ID: " + student2.getString("studentId") + " Name: " + student2.getString("studentFirstName") + " " + student2.getString("studentLastName") );
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

}
