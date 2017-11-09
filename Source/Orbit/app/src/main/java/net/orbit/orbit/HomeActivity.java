package net.orbit.orbit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        final Button button = (Button) findViewById(R.id.createStudent);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent createStudentIntent= new Intent(HomeActivity.this, CreateStudentActivity.class);
                HomeActivity.this.startActivity(createStudentIntent);
            }
        });

        // Displays a alert window and lets you know if your DB connection is successful.
        // If student data is returned, then the connection was successful.
        getDBConnectionAlert();

    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        return i;
    }

    public void getDBConnectionAlert(){
        orbitRestClient.get("all-students", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray students) {
                Log.i("HomeActivity", "Successfully connected to DB.");
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("DB Connection");
                alertDialog.setMessage("Successfully retrieved data from API: " + orbitRestClient.getBaseUrl());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }});
                alertDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("HomeActivity", "Error connection to DB: " + errorResponse.toString());
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("DB Connection");
                alertDialog.setMessage("Cannot connect to DB: " + orbitRestClient.getBaseUrl());
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
                alertDialog.show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

}
