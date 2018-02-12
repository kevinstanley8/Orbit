package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.R;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.Constants;

/**
 * Created by David on 11/8/2017.
 */

public class CreateStudentActivity extends BaseActivity
{
    private StudentService studentService = new StudentService(this);

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, CreateStudentActivity.class);
        return i;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_student);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_create_student, relativeLayout);

        final Button createStudent = (Button) findViewById(R.id.createStudent);
        final Button cancelButton = (Button) findViewById(R.id.cancel_action);

        final EditText studentFirstName = (EditText) findViewById(R.id.studentFirstName);
        final EditText studentLastName = (EditText) findViewById(R.id.studentLastName);
        final EditText studentDOB = (EditText) findViewById(R.id.studentDateOfBirth);
        final EditText studentSSN = (EditText) findViewById(R.id.studentSSN);
        final EditText studentAddress1 = (EditText) findViewById(R.id.studentAddress1);
        final EditText studentAddress2 = (EditText) findViewById(R.id.studentAddress2);
        final EditText studentCity = (EditText) findViewById(R.id.studentCity);
        final EditText studentState = (EditText) findViewById(R.id.studentState);
        final EditText studentZipCode = (EditText) findViewById(R.id.studentZip);
        final String studentGrade = Constants.FILLOUT_LATER;

        final String motherFirstName = Constants.FILLOUT_LATER;
        final String motherLastName = Constants.FILLOUT_LATER;
        final String motherSSN = Constants.FILLOUT_LATER;
        final String motherAddress1 = Constants.FILLOUT_LATER;
        final String motherAddress2 = Constants.FILLOUT_LATER;
        final String motherCity = Constants.FILLOUT_LATER;
        final String motherState = Constants.FILLOUT_LATER;
        final String motherZipCode = Constants.FILLOUT_LATER;
        final String motherHomePhone = Constants.FILLOUT_LATER;
        final String motherCellPhone = Constants.FILLOUT_LATER;
        final String motherEmail = Constants.FILLOUT_LATER;

        final String fatherFirstName = Constants.FILLOUT_LATER;
        final String fatherLastName = Constants.FILLOUT_LATER;
        final String fatherSSN = Constants.FILLOUT_LATER;
        final String fatherAddress1 = Constants.FILLOUT_LATER;
        final String fatherAddress2 = Constants.FILLOUT_LATER;
        final String fatherCity = Constants.FILLOUT_LATER;
        final String fatherState = Constants.FILLOUT_LATER;
        final String fatherZipCode = Constants.FILLOUT_LATER;
        final String fatherHomePhone = Constants.FILLOUT_LATER;
        final String fatherCellPhone = Constants.FILLOUT_LATER;
        final String fatherEmail = Constants.FILLOUT_LATER;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Cancel adding new Teacher.");
                finish();
            }

        });

        createStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CreateStudentActivity", "Create A New Student.");

                //Creates a new menu_student Object to send to API
                Student newStudent = new Student(
                                studentFirstName.getText().toString(),
                                studentLastName.getText().toString(),
                                studentDOB.getText().toString(),
                                studentSSN.getText().toString(),
                                studentAddress1.getText().toString(),
                                studentAddress2.getText().toString(),
                                studentCity.getText().toString(),
                                studentState.getText().toString(),
                                studentZipCode.getText().toString(),
                                studentGrade,
                                motherFirstName,
                                motherLastName,
                                motherSSN,
                                motherAddress1,
                                motherAddress2,
                                motherCity,
                                motherState,
                                motherZipCode,
                                motherHomePhone,
                                motherCellPhone,
                                motherEmail,
                                fatherFirstName,
                                fatherLastName,
                                fatherSSN,
                                fatherAddress1,
                                fatherAddress2,
                                fatherCity,
                                fatherState,
                                fatherZipCode,
                                fatherHomePhone,
                                fatherCellPhone,
                                fatherEmail,
                                0
                                );

                studentService.addStudent(newStudent);

            }
        });
    }
}