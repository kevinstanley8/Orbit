package net.orbit.orbit.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.R;
import net.orbit.orbit.services.PropertiesService;
import net.orbit.orbit.utils.OrbitRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends BaseActivity {



    PropertiesService propertiesService = new PropertiesService();
    OrbitRestClient orbitRestClient = new OrbitRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_home, relativeLayout);


         GridView gridview = (GridView) findViewById(R.id.gridview);
         class ImageAdapter extends BaseAdapter
         {
             private Context mContext;
             private final String[] labels;
             private final int[] Imageid;

             public ImageAdapter(Context c,String[] labels,int[] Imageid ) {
                 mContext = c;
                 this.Imageid = Imageid;
                 this.labels = labels;
             }

             @Override
             public int getCount() {
                 // TODO Auto-generated method stub
                 return labels.length;
             }

             @Override
             public Object getItem(int position) {
                 // TODO Auto-generated method stub
                 return null;
             }

             @Override
             public long getItemId(int position) {
                 // TODO Auto-generated method stub
                 return 0;
             }

             @Override
             public View getView(int position, View convertView, ViewGroup parent) {
                 // TODO Auto-generated method stub
                 View grid;
                 LayoutInflater inflater = (LayoutInflater) mContext
                         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                 if (convertView == null) {

                     grid = new View(mContext);
                     grid = inflater.inflate(R.layout.grid_item, null);
                     TextView textView = (TextView) grid.findViewById(R.id.gridText);
                     ImageView imageView = (ImageView)grid.findViewById(R.id.gridImage);
                     textView.setText(labels[position]);
                     imageView.setImageResource(Imageid[position]);
                 } else {
                     grid = (View) convertView;
                 }

                 return grid;
             }
            }

            // references to our images
        final String[] labels=
                {
                        "Link Students",
                        "Select Student",
                        "Log-Out",
                        "Filler",
                        "Test",
                        "Test"

                };
        int[] images =
                {
                        R.drawable.menu_link_parent_student,
                        R.drawable.menu_choose_student,
                        R.drawable.menu_logout,
                        R.drawable.menu_school,
                        R.drawable.menu_student,
                        R.drawable.menu_teacher
                };

        gridview.setAdapter(new ImageAdapter(this, labels, images));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id)
            {
                if(labels[position] == "Log-Out")
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent newIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(newIntent);
                }

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
                
                // TODO GP-121 testing roles
                //rs.hasTeacherRole();
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
