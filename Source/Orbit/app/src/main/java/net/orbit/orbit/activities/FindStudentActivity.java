package net.orbit.orbit.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.StudentDTO;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitUserPreferences;
import net.orbit.orbit.utils.PopupMessages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindStudentActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, FindStudentActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_student);
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_find_student, relativeLayout);

        final RelativeLayout findStudent = (RelativeLayout) findViewById(R.id.findStudent);

        final EditText studentFirstName = (EditText) findViewById(R.id.studentFirstName);
        final EditText studentLastName = (EditText) findViewById(R.id.studentLastName);
        final TextView studentDOB = (TextView) findViewById(R.id.studentDateOfBirth);

        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        studentDOB.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){
                    // Initialize a new date picker dialog fragment
                    DialogFragment dFragment = new FindStudentActivity.DatePickerFragment();

                    // Show the date picker dialog fragment
                    dFragment.show(getFragmentManager(), "Date Picker");
                    return true;
                }
                return false;

            }
        });

        findStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("FindStudentActivity", "Finding a Student.");

                View focusView = null;
                if (TextUtils.isEmpty(studentFirstName.getText().toString())) {
                    studentFirstName.setError(getString(R.string.error_field_required));
                    focusView = studentFirstName;
                    focusView.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(studentLastName.getText().toString())) {
                    studentLastName.setError(getString(R.string.error_field_required));
                    focusView = studentLastName;
                    focusView.requestFocus();
                    return;
                }
                if (studentDOB.getText().toString().equals("")) {
                    studentDOB.setError(getString(R.string.error_field_required));
                    focusView = studentDOB;
                    focusView.requestFocus();
                    return;
                } else {
                    studentDOB.setError(null);
                }
                //Creates a new menu_student Object to send to API
                StudentDTO studentDto = new StudentDTO(
                        studentFirstName.getText().toString(),
                        studentLastName.getText().toString(),
                        studentDOB.getText().toString()
                );


                StudentService studentService = new StudentService(FindStudentActivity.this);
                studentService.findStudent(studentDto);

            }
        });
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                We should use THEME_HOLO_LIGHT, THEME_HOLO_DARK or THEME_TRADITIONAL only.

                The THEME_DEVICE_DEFAULT_LIGHT and THEME_DEVICE_DEFAULT_DARK does not work
                perfectly. This two theme set disable color of disabled dates but users can
                select the disabled dates also.

                Other three themes act perfectly after defined enabled date range of date picker.
                Those theme completely hide the disable dates from date picker object.
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            // Add 3 days to Calendar
            calendar.add(Calendar.DATE, 1);


            // Set the Calendar new date as maximum date of date picker
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            // Subtract 6 days from Calendar updated date
            calendar.add(Calendar.DATE, -10000);

            // Set the Calendar new date as minimum date of date picker
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());

            // Return the DatePickerDialog
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
            TextView date = (TextView) getActivity().findViewById(R.id.studentDateOfBirth);

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            String formattedDate = new SimpleDateFormat(Constants.DATE_FORMAT).format(chosenDate);

            // Display the chosen date to app interface
            date.setText(formattedDate);
        }
    }
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.FIND_STUDENT_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
