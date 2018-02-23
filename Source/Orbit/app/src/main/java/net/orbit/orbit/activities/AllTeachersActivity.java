package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.services.TeacherService;

public class AllTeachersActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, AllTeachersActivity.class);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all_teachers);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_all_teachers, relativeLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TeacherService teacherService = new TeacherService(this);
        teacherService.viewTeachers(this);
    }


    public void updateTeacherList(Teacher[] teacherList)
    {
        boolean first = true;
        StringBuilder teacherNames = new StringBuilder();

        for(int i = 0; i < teacherList.length; i++)
        {
            if(!first)
                teacherNames.append(", ");
            else
                first = false;

            teacherNames.append(teacherList[i].getFirstName());
            teacherNames.append(" ");
            teacherNames.append(teacherList[i].getLastName());
        }

        final TextView teachers = (TextView)findViewById(R.id.teachers);
        teachers.setText(teacherNames.toString());
    }

}
