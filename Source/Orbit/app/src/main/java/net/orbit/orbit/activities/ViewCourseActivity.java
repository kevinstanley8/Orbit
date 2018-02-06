package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.orbit.orbit.R;

public class ViewCourseActivity extends BaseActivity {


    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, ViewCourseActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_course, relativeLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent(); // gets the previously created intent
        TextView txtCourseName = (TextView) findViewById(R.id.txtCourseName);
        txtCourseName.setText(myIntent.getStringExtra("courseName"));


    }
}
