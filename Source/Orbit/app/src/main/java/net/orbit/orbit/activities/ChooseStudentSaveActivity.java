package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.orbit.orbit.utils.OrbitUserPreferences;

public class ChooseStudentSaveActivity extends BaseActivity {
    private static int studentID = 0;

    public static Intent createIntent(Context context, int studentID) {
        Intent i = new Intent(context, ChooseStudentSaveActivity.class);
        ChooseStudentSaveActivity.studentID = studentID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        OrbitUserPreferences orbitPref = new OrbitUserPreferences(this);
//        orbitPref.storePreference("chosenStudentID", ChooseStudentSaveActivity.studentID);
//
//        Intent intent = getIntent();
//        Intent intentNext = CourseGradesActivity.createIntent(this);
//        intentNext.putExtra("studentFullName", intent.getStringExtra("studentFullName"));
//        this.startActivity(intentNext);
        //setContentView(R.layout.activity_choose_student_save);
    }
}
