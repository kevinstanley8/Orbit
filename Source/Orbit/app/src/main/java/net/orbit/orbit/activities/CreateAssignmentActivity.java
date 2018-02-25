package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.services.AssignmentService;

public class CreateAssignmentActivity extends BaseActivity {
    public static int courseID;

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, CreateAssignmentActivity.class);
        CreateAssignmentActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_create_assignment, relativeLayout);
        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }

        });
        findViewById(R.id.btnCreateAssignment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAssignment();
            }
        });
    }

    private void createAssignment()
    {
        EditText txtAssignmentName;
        EditText txtMaxPoints;

        txtAssignmentName = (EditText)findViewById(R.id.txtAssignmentName);
        txtMaxPoints = (EditText)findViewById(R.id.txtMaxPoints);

        View focusView = null;

        if (TextUtils.isEmpty(txtAssignmentName.getText().toString())) {
            txtAssignmentName.setError(getString(R.string.error_field_required));
            focusView = txtAssignmentName;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(txtMaxPoints.getText().toString())) {
            txtMaxPoints.setError(getString(R.string.error_field_required));
            focusView = txtMaxPoints;
            focusView.requestFocus();
            return;
        }

        CreateAssignmentDTO assignmentDTO = new CreateAssignmentDTO(CreateAssignmentActivity.courseID, txtAssignmentName.getText().toString(), txtMaxPoints.getText().toString());
        AssignmentService assignmentService = new AssignmentService(this);
        assignmentService.createAssignment(assignmentDTO);
    }
}
