package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.orbit.orbit.models.pojo.Course;

import net.orbit.orbit.R;
import net.orbit.orbit.services.CourseService;

import java.util.List;

public class ViewCoursesTeacherActivity extends BaseActivity {

    CourseService courseService = new CourseService(this);

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, ViewCoursesTeacherActivity.class);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_courses_teacher, relativeLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseService.getAllCourses(this);


        FloatingActionButton mFabAddCourse = (FloatingActionButton) findViewById(R.id.fab_add_course);
        mFabAddCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ViewCoursesTeacherActivity", "We want to add a new Course.");
                Intent chooseCourseActivity = new Intent(ViewCoursesTeacherActivity.this, ChooseCourseActivity.class);
                ViewCoursesTeacherActivity.this.startActivity(chooseCourseActivity);
            }

        });
    }

    public void updateCourseList(List<Course> courseList){
        if(courseList.size() == 0){
            Log.i("ViewCourseActivity", "No courses found for teacher logged in.");
            Toast.makeText(this, "You have no courses." , Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("ViewCourseActivity", "Found courses associated with teacher logged in." + courseList);



    }


}