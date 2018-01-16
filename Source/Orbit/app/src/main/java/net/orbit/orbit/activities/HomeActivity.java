package net.orbit.orbit.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.R;
import net.orbit.orbit.services.PropertiesService;
import net.orbit.orbit.services.RoleService;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends BaseActivity {



    PropertiesService propertiesService = new PropertiesService();
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    RoleService rs = new RoleService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_home, relativeLayout);

        // Determines the screen size of the current device
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        int dpHeight = (int) (outMetrics.heightPixels / density);
        int dpWidth  = (int) (outMetrics.widthPixels / density);

    // TODO still need to play with this to ensure scaling...
        // maybe go a different route
        int testWidth = (dpWidth) ;

        ViewGroup.LayoutParams params;
        final ImageButton firstIcon = (ImageButton) findViewById(R.id.firstIcon);
        final ImageButton secondIcon = (ImageButton) findViewById(R.id.secondIcon);
        final ImageButton thirdIcon = (ImageButton) findViewById(R.id.thirdIcon);
        final ImageButton fourthIcon = (ImageButton) findViewById(R.id.fourthIcon);
        final ImageButton fifthIcon = (ImageButton) findViewById(R.id.fifthIcon);
        final ImageButton sixthIcon = (ImageButton) findViewById(R.id.sixthIcon);
        final ImageButton seventhIcon = (ImageButton) findViewById(R.id.seventhIcon);
        final ImageButton eighthIcon = (ImageButton) findViewById(R.id.eighthIcon);
        final ImageButton ninthIcon = (ImageButton) findViewById(R.id.ninthIcon);

// TODO assign actual listeners to different pages
        params = firstIcon.getLayoutParams();
        params.height = testWidth;
        params.width = testWidth;
        firstIcon.setLayoutParams(params);

        firstIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = secondIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        secondIcon.setLayoutParams(params);

        secondIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });


        params = thirdIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        thirdIcon.setLayoutParams(params);

        thirdIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = fourthIcon.getLayoutParams();
        params.height = testWidth;
        params.width = testWidth;
        fourthIcon.setLayoutParams(params);

        fourthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = fifthIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        fifthIcon.setLayoutParams(params);

        fifthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = sixthIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        sixthIcon.setLayoutParams(params);

        sixthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = seventhIcon.getLayoutParams();
        params.height = testWidth;
        params.width = testWidth;
        seventhIcon.setLayoutParams(params);

        seventhIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = eighthIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        eighthIcon.setLayoutParams(params);

        eighthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        params = ninthIcon.getLayoutParams();
        params.width = testWidth;
        params.height = testWidth;
        ninthIcon.setLayoutParams(params);

        ninthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(newIntent);
            }
        });

        // Sets the URL for the API url
        String apiUrl = propertiesService.getProperty(this, "orbit.api.url");
        orbitRestClient.setBaseUrl(apiUrl);

        // Displays a alert window and lets you know if your DB connection is successful.
        // If menu_student data is returned, then the connection was successful.
        getDBConnectionAlert();

    }


    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, HomeActivity.class);
        return i;
    }

    public void getDBConnectionAlert() {
        orbitRestClient.get("all-students", null, new JsonHttpResponseHandler() {
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
                            }
                        });
                alertDialog.show();
                rs.hasTeacherRole();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                if (errorResponse != null) {
                    Log.e("HomeActivity", "Error connection to DB: " + errorResponse.toString());
                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                    alertDialog.setTitle("DB Connection");
                    alertDialog.setMessage("Cannot connect to DB: " + orbitRestClient.getBaseUrl());
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

}