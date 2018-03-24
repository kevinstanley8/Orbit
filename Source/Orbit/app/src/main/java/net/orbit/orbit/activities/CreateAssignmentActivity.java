package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.CreateAssignmentDTO;
import net.orbit.orbit.services.AssignmentService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

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

        context = this;
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
        EditText txtDescription;

        txtAssignmentName = (EditText)findViewById(R.id.txtAssignmentName);
        txtDescription = (EditText)findViewById(R.id.txtDescription);
        txtMaxPoints = (EditText)findViewById(R.id.txtMaxPoints);

        View focusView = null;

        if (TextUtils.isEmpty(txtAssignmentName.getText().toString())) {
            txtAssignmentName.setError(getString(R.string.error_field_required));
            focusView = txtAssignmentName;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(txtDescription.getText().toString())) {
            txtDescription.setError(getString(R.string.error_field_required));
            focusView = txtDescription;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(txtMaxPoints.getText().toString())) {
            txtMaxPoints.setError(getString(R.string.error_field_required));
            focusView = txtMaxPoints;
            focusView.requestFocus();
            return;
        }

        CreateAssignmentDTO assignmentDTO = new CreateAssignmentDTO(CreateAssignmentActivity.courseID, txtAssignmentName.getText().toString(), txtMaxPoints.getText().toString(), txtDescription.getText().toString());
        AssignmentService assignmentService = new AssignmentService(this);
        assignmentService.createAssignment(assignmentDTO);
    }
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.CREATE_ASSIGNMENT_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
