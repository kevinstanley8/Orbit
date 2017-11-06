package net.orbit.orbit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import net.orbit.orbit.Model.Teacher;
import net.orbit.orbit.Service.PropertiesService;
import net.orbit.orbit.Utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AddTeacherActivity extends AppCompatActivity {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        // Sets the URL for the API url
        String apiUrl = propertiesService.getProperty(this,"orbit.api.url");
        orbitRestClient.setBaseUrl(apiUrl);

        final Button addTeacherButton = (Button) findViewById(R.id.addTeacher);
        final Button cancelButton = (Button) findViewById(R.id.cancel_action);

        final EditText mFirstName   = (EditText)findViewById(R.id.firstName);
        final EditText mLastName   = (EditText)findViewById(R.id.lastName);
        final EditText mDob   = (EditText)findViewById(R.id.dateOfBirth);
        final EditText mSsn   = (EditText)findViewById(R.id.ssn);
        final EditText mAddress1   = (EditText)findViewById(R.id.address1);
        final EditText mAddress2   = (EditText)findViewById(R.id.address2);
        final EditText mCity   = (EditText)findViewById(R.id.city);
        final EditText mState   = (EditText)findViewById(R.id.state);
        final EditText mZip   = (EditText)findViewById(R.id.zip);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Cancel adding new Teacher.");
                finish();
            }

        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Add a new Test Teacher.");

                //Creates a new teacher Object to send to API
                Teacher newTeacher = new Teacher
                    (mFirstName.getText().toString(),
                    mLastName.getText().toString(),
                    mDob.getText().toString(),
                    mSsn.getText().toString(),
                    mAddress1.getText().toString(),
                    mAddress2.getText().toString(),
                    mCity.getText().toString(),
                    mState.getText().toString(),
                    mZip.getText().toString());
                Gson gson = new Gson();
                String json = gson.toJson(newTeacher);
                StringEntity entity = null;
                try {
                    entity = new StringEntity(json.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                orbitRestClient.post(AddTeacherActivity.this, "add-teacher", entity, "application/json",
                        new JsonHttpResponseHandler(){
                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray teacher) {
                                // called when success happens
                                Log.i("AddTeacherActivity", "Successfully added new teacher: " + teacher);

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                Log.e("AddTeacherActivity", "Error when adding new teacher: " + errorResponse);
                            }

                            @Override
                            public void onRetry(int retryNo) {
                                // called when request is retried
                            }
                        });
            }
        });
    }
}
