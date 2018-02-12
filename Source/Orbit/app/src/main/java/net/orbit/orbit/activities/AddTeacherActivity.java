package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.R;
import net.orbit.orbit.services.TeacherService;
import net.orbit.orbit.utils.Constants;

public class AddTeacherActivity extends BaseActivity {
    TeacherService teacherService = new TeacherService(this);

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, AddTeacherActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_teacher);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_add_teacher, relativeLayout);

        final Button addTeacherButton = (Button) findViewById(R.id.addTeacher);
        final Button cancelButton = (Button) findViewById(R.id.cancel_action);

        final EditText mFirstName   = (EditText)findViewById(R.id.firstName);
        final EditText mLastName   = (EditText)findViewById(R.id.lastName);
        final String mDob   = Constants.FILLOUT_LATER;
        final EditText mSsn   = (EditText)findViewById(R.id.ssn);
        final String mAddress1   = Constants.FILLOUT_LATER;
        final String mAddress2   = Constants.FILLOUT_LATER;
        final String mCity   = Constants.FILLOUT_LATER;
        final String mState   = Constants.FILLOUT_LATER;
        final String mZip   = Constants.FILLOUT_LATER;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Cancel adding new Teacher.");
                finish();
            }

        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Add a new Test Teacher.");

                //Creates a new menu_teacher Object to send to API
                Teacher newTeacher = new Teacher
                            (mFirstName.getText().toString(),
                                    mLastName.getText().toString(),
                                    mDob,
                                    mSsn.getText().toString(),
                                    mAddress1,
                                    mAddress2,
                                    mCity,
                                    mState,
                                    mZip);

                teacherService.addTeacher(newTeacher);

            }
        });
    }
}
