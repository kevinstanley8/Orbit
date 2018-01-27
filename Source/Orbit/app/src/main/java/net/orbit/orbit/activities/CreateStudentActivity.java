package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.orbit.orbit.models.Student;
import net.orbit.orbit.R;
import net.orbit.orbit.services.StudentService;

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
        final String studentGrade = "To Be Filled at Later Date";

        final String motherFirstName = "To Be Filled at Later Date";
        final String motherLastName = "To Be Filled at Later Date";
        final String motherSSN = "To Be Filled at Later Date";
        final String motherAddress1 = "To Be Filled at Later Date";
        final String motherAddress2 = "To Be Filled at Later Date";
        final String motherCity = "To Be Filled at Later Date";
        final String motherState = "To Be Filled at Later Date";
        final String motherZipCode = "To Be Filled at Later Date";
        final String motherHomePhone = "To Be Filled at Later Date";
        final String motherCellPhone = "To Be Filled at Later Date";
        final String motherEmail = "";

        final String fatherFirstName = "To Be Filled at Later Date";
        final String fatherLastName = "To Be Filled at Later Date";
        final String fatherSSN = "To Be Filled at Later Date";
        final String fatherAddress1 = "To Be Filled at Later Date";
        final String fatherAddress2 = "To Be Filled at Later Date";
        final String fatherCity = "To Be Filled at Later Date";
        final String fatherState = "To Be Filled at Later Date";
        final String fatherZipCode = "To Be Filled at Later Date";
        final String fatherHomePhone = "To Be Filled at Later Date";
        final String fatherCellPhone = "To Be Filled at Later Date";
        final String fatherEmail = "To Be Filled at Later Date";

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