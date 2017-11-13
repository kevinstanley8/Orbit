package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.orbit.orbit.models.Teacher;
import net.orbit.orbit.R;
import net.orbit.orbit.services.TeacherService;

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
        final String mFirstrgName = String.valueOf((EditText)findViewById(R.id.firstName));
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

                //Creates a new menu_teacher Object to send to API
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

                teacherService.addTeacher(newTeacher);

            }
        });
    }
}
