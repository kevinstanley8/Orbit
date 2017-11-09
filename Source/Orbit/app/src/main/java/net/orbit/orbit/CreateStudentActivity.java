package net.orbit.orbit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.orbit.orbit.Model.Student;
import net.orbit.orbit.Service.StudentService;

/**
 * Created by David on 11/8/2017.
 */

public class CreateStudentActivity extends AppCompatActivity
{
    private StudentService studentService = new StudentService(this);
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

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
        final EditText studentGrade = (EditText) findViewById(R.id.studentGrade);

        final EditText motherFirstName = (EditText) findViewById(R.id.motherFirstName);
        final EditText motherLastName = (EditText) findViewById(R.id.motherLastName);
        final EditText motherSSN = (EditText) findViewById(R.id.motherSSN);
        final EditText motherAddress1 = (EditText) findViewById(R.id.motherAddress1);
        final EditText motherAddress2 = (EditText) findViewById(R.id.motherAddress2);
        final EditText motherCity = (EditText) findViewById(R.id.motherCity);
        final EditText motherState = (EditText) findViewById(R.id.motherState);
        final EditText motherZipCode = (EditText) findViewById(R.id.motherZip);
        final EditText motherHomePhone = (EditText) findViewById(R.id.motherHomePhone);
        final EditText motherCellPhone = (EditText) findViewById(R.id.motherCellPhone);
        final EditText motherEmail = (EditText) findViewById(R.id.motherEmail);

        final EditText fatherFirstName = (EditText) findViewById(R.id.fatherFirstName);
        final EditText fatherLastName = (EditText) findViewById(R.id.fatherLastName);
        final EditText fatherSSN = (EditText) findViewById(R.id.fatherSSN);
        final EditText fatherAddress1 = (EditText) findViewById(R.id.fatherAddress1);
        final EditText fatherAddress2 = (EditText) findViewById(R.id.fatherAddress2);
        final EditText fatherCity = (EditText) findViewById(R.id.fatherCity);
        final EditText fatherState = (EditText) findViewById(R.id.fatherState);
        final EditText fatherZipCode = (EditText) findViewById(R.id.fatherZip);
        final EditText fatherHomePhone = (EditText) findViewById(R.id.fatherHomePhone);
        final EditText fatherCellPhone = (EditText) findViewById(R.id.fatherCellPhone);
        final EditText fatherEmail = (EditText) findViewById(R.id.fatherEmail);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("AddTeacherActivity", "Cancel adding new Teacher.");
                finish();
            }

        });

        createStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CreateStudentActivity", "Create A New Student.");

                //Creates a new student Object to send to API
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
                                studentGrade.getText().toString(),
                                motherFirstName.getText().toString(),
                                motherLastName.getText().toString(),
                                motherSSN.getText().toString(),
                                motherAddress1.getText().toString(),
                                motherAddress2.getText().toString(),
                                motherCity.getText().toString(),
                                motherState.getText().toString(),
                                motherZipCode.getText().toString(),
                                motherHomePhone.getText().toString(),
                                motherCellPhone.getText().toString(),
                                motherEmail.getText().toString(),
                                fatherFirstName.getText().toString(),
                                fatherLastName.getText().toString(),
                                fatherSSN.getText().toString(),
                                fatherAddress1.getText().toString(),
                                fatherAddress2.getText().toString(),
                                fatherCity.getText().toString(),
                                fatherState.getText().toString(),
                                fatherZipCode.getText().toString(),
                                fatherHomePhone.getText().toString(),
                                fatherCellPhone.getText().toString(),
                                fatherEmail.getText().toString()
                                );

                studentService.addStudent(newStudent);

            }
        });
    }
}