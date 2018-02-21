package net.orbit.orbit.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.orbit.orbit.models.exceptions.ErrorResponse;
import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Teacher;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.ServerCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David on 11/8/2017.
 */

public class CreateStudentActivity extends BaseActivity
{
    private StudentService studentService = new StudentService(this);
    private int mYear,mMonth,mDay;

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

        final RelativeLayout createStudent = (RelativeLayout) findViewById(R.id.createStudent);
        final RelativeLayout cancelButton = (RelativeLayout) findViewById(R.id.cancel_action);

        final EditText studentFirstName = (EditText) findViewById(R.id.studentFirstName);
        final EditText studentLastName = (EditText) findViewById(R.id.studentLastName);
//        final EditText studentSSN = (EditText) findViewById(R.id.studentSSN);
//        final EditText studentAddress1 = (EditText) findViewById(R.id.studentAddress1);
//        final EditText studentAddress2 = (EditText) findViewById(R.id.studentAddress2);
//        final EditText studentCity = (EditText) findViewById(R.id.studentCity);
//        final EditText studentState = (EditText) findViewById(R.id.studentState);
//        final EditText studentZipCode = (EditText) findViewById(R.id.studentZip);
        final TextView dateTextView = (TextView) findViewById(R.id.studentDateOfBirth);
//        final String studentGrade = Constants.FILLOUT_LATER;
//
//        final String motherFirstName = Constants.FILLOUT_LATER;
//        final String motherLastName = Constants.FILLOUT_LATER;
//        final String motherSSN = Constants.FILLOUT_LATER;
//        final String motherAddress1 = Constants.FILLOUT_LATER;
//        final String motherAddress2 = Constants.FILLOUT_LATER;
//        final String motherCity = Constants.FILLOUT_LATER;
//        final String motherState = Constants.FILLOUT_LATER;
//        final String motherZipCode = Constants.FILLOUT_LATER;
//        final String motherHomePhone = Constants.FILLOUT_LATER;
//        final String motherCellPhone = Constants.FILLOUT_LATER;
//        final String motherEmail = Constants.FILLOUT_LATER;
//
//        final String fatherFirstName = Constants.FILLOUT_LATER;
//        final String fatherLastName = Constants.FILLOUT_LATER;
//        final String fatherSSN = Constants.FILLOUT_LATER;
//        final String fatherAddress1 = Constants.FILLOUT_LATER;
//        final String fatherAddress2 = Constants.FILLOUT_LATER;
//        final String fatherCity = Constants.FILLOUT_LATER;
//        final String fatherState = Constants.FILLOUT_LATER;
//        final String fatherZipCode = Constants.FILLOUT_LATER;
//        final String fatherHomePhone = Constants.FILLOUT_LATER;
//        final String fatherCellPhone = Constants.FILLOUT_LATER;
//        final String fatherEmail = Constants.FILLOUT_LATER;

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
                                dateTextView.getText().toString());

                studentService.addStudent(newStudent, new ServerCallback<Student>(){

                    @Override
                    public void onSuccess(Student result) {
                        Toast.makeText(CreateStudentActivity.this, "Student " + result.getStudentFirstName() + " " + result.getStudentLastName() + " created.",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(ErrorResponse errorMessage) {
                        Toast.makeText(CreateStudentActivity.this, "Student creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dateTextView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){
                    // Initialize a new date picker dialog fragment
                    DialogFragment dFragment = new CreateStudentActivity.DatePickerFragment();

                    // Show the date picker dialog fragment
                    dFragment.show(getFragmentManager(), "Date Picker");
                    return true;
                }
                return false;

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
}