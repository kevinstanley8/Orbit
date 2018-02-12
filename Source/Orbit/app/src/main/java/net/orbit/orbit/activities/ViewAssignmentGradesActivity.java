package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.orbit.orbit.R;

public class ViewAssignmentGradesActivity extends BaseActivity {
    public static int assignmentID = 0;

    public static Intent createIntent(Context context, int assignmentID) {
        Intent i = new Intent(context, ViewAssignmentGradesActivity.class);
        ViewAssignmentGradesActivity.assignmentID = assignmentID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_assignment_grades, relativeLayout);

        TextView txtAssignmentName = (TextView)findViewById(R.id.txtAssignmentName);
        txtAssignmentName.setText("GRADES FOR ASSIGNMENT " + ViewAssignmentGradesActivity.assignmentID + " GO HERE");
    }
}
