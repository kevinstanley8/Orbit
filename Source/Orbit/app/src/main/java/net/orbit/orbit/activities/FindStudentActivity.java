package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.StudentDTO;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.OrbitUserPreferences;

public class FindStudentActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, FindStudentActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_student);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_find_student, relativeLayout);

        final RelativeLayout findStudent = (RelativeLayout) findViewById(R.id.findStudent);

        final EditText studentFirstName = (EditText) findViewById(R.id.studentFirstName);
        final EditText studentLastName = (EditText) findViewById(R.id.studentLastName);
        final EditText studentDOB = (EditText) findViewById(R.id.studentDateOfBirth);
        final EditText studentSSN = (EditText) findViewById(R.id.studentSSN);

        findStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("FindStudentActivity", "Finding a Student.");

                //Creates a new menu_student Object to send to API
                StudentDTO studentDto = new StudentDTO(
                        studentFirstName.getText().toString(),
                        studentLastName.getText().toString(),
                        studentDOB.getText().toString(),
                        studentSSN.getText().toString()
                );


                StudentService studentService = new StudentService(getApplicationContext());
                OrbitUserPreferences orbitPref = new OrbitUserPreferences(getApplicationContext());
                String uid = orbitPref.getStringPreference("userUID");
                studentService.findStudent(studentDto, uid);

            }
        });
    }
}
