package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.R;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.services.TeacherService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.PopupMessages;

public class AddTeacherActivity extends BaseActivity {


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

        final RelativeLayout addTeacherButton = (RelativeLayout) findViewById(R.id.addTeacher);
        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);

        final EditText mFirstName   = (EditText)findViewById(R.id.firstName);
        final EditText mLastName   = (EditText)findViewById(R.id.lastName);
        final EditText mSsn   = (EditText)findViewById(R.id.ssn);
        final String mDob   = Constants.FILLOUT_LATER;
        final String mAddress1   = Constants.FILLOUT_LATER;
        final String mAddress2   = Constants.FILLOUT_LATER;
        final String mCity   = Constants.FILLOUT_LATER;
        final String mState   = Constants.FILLOUT_LATER;
        final String mZip   = Constants.FILLOUT_LATER;

        context = this;


        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Cancel adding new Teacher.");
                finish();
            }

        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Add a new Test Teacher.");

                View focusView = null;
                if (TextUtils.isEmpty(mFirstName.getText().toString())) {
                    mFirstName.setError(getString(R.string.error_field_required));
                    focusView = mFirstName;
                    focusView.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mLastName.getText().toString())) {
                    mLastName.setError(getString(R.string.error_field_required));
                    focusView = mLastName;
                    focusView.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mSsn.getText().toString())) {
                    mSsn.setError(getString(R.string.error_field_required));
                    focusView = mSsn;
                    focusView.requestFocus();
                    return;
                }

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

                TeacherService teacherService = new TeacherService(AddTeacherActivity.this);
                teacherService.addTeacher(newTeacher);

            }
        });
    }
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.ADD_TEACHER_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
