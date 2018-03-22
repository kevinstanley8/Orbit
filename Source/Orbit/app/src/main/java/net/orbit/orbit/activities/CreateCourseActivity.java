package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import net.orbit.orbit.R;
import net.orbit.orbit.services.CourseService;
import net.orbit.orbit.services.PopupService;

public class CreateCourseActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, CreateCourseActivity.class);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_create_course, relativeLayout);
        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }

        });
        findViewById(R.id.btnCreateCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });
        context = this;
    }

    private void createCourse()
    {
        EditText txtCourseName;

        txtCourseName = (EditText)findViewById(R.id.txtCourseName);

        View focusView = null;

        if (TextUtils.isEmpty(txtCourseName.getText().toString())) {
            txtCourseName.setError(getString(R.string.error_field_required));
            focusView = txtCourseName;
            focusView.requestFocus();
            return;
        }

        CourseService courseService = new CourseService(this);
        courseService.createCourse(txtCourseName.getText().toString());
    }
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup("Create a new course.");
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
