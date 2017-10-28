package net.orbit.orbit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.loopj.android.http.*;
import org.json.*;
import net.orbit.orbit.HttpUtils.OrbitRestClient;
import cz.msebera.android.httpclient.Header;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final TextView textView = (TextView)findViewById(R.id.students);

        OrbitRestClient.get("all-students", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray students) {
                Log.i("HomeActivity", "Successfully received JSONArray with all students!");
                try {
                    JSONObject student1 = students.getJSONObject(0);
                    textView.append("ID: " + student1.getString("id") + " Name: " + student1.getString("firstName") + " " + student1.getString("lastName")  + "\n");
                    JSONObject student2 = students.getJSONObject(1);
                    textView.append("ID: " + student2.getString("id") + " Name: " + student2.getString("firstName") + " " + student2.getString("lastName") );
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
