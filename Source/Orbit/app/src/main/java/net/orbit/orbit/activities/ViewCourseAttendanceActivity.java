package net.orbit.orbit.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.SaveAttendanceDTO;
import net.orbit.orbit.models.pojo.Attendance;
import net.orbit.orbit.services.AttendanceService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.PopupMessages;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class ViewCourseAttendanceActivity extends BaseActivity{
    private static int courseID = 0;
    private Context context;
    private ListView listView;
    private List<Attendance> attendanceList = new ArrayList<>();
    private Date today;

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, ViewCourseAttendanceActivity.class);
        ViewCourseAttendanceActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_course_attendance, relativeLayout);

        listView = (ListView) findViewById(R.id.listView);
        ListAdapter customAdapter = new ListAdapter(this, R.layout.teacher_attendance_item, attendanceList);
        listView.setAdapter(customAdapter);
        TextView dateTextView = (TextView) findViewById(R.id.date);

        if(courseID == 0)
        {
            Intent intent = ViewCoursesTeacherActivity.createIntent(this);
            intent.putExtra("actionType", 2);
            this.startActivity(intent);
        }

        today = new Date(Calendar.getInstance().getTimeInMillis());
        dateTextView.setText(today.toString());
        findViewById(R.id.btnSaveAttendance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAttendance();
            }
        });

        AttendanceService attendanceService= new AttendanceService(this);
        attendanceService.getCourseAttendance(this, courseID, today);
    }

    public void saveAttendance()
    {
        View childView;
        Spinner statusSpinner;
        SaveAttendanceDTO saveAttendanceDTO = new SaveAttendanceDTO();

        for(int i = 0; i < listView.getChildCount(); i++)
        {
            childView = listView.getChildAt(i);
            statusSpinner = (Spinner)childView.findViewById(R.id.statusSpinner);
            // SPINNER VALUE
            this.attendanceList.get(i).setStatus(statusSpinner.getSelectedItem().toString());
            this.attendanceList.get(i).setDate(today);
            saveAttendanceDTO.addAttendance(this.attendanceList.get(i));
        }
        AttendanceService attendanceService = new AttendanceService(this);
        attendanceService.saveAttendance(saveAttendanceDTO);

        //This works, cheat and cheap, annoying for user
        startActivity(createIntent(this, courseID));
    }

    public class ListAdapter extends ArrayAdapter<Attendance>
    {
        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Attendance> attendanceList) {
            super(context, resource, attendanceList);
        }
        @Override
        public View getView(int position, View convertView, final ViewGroup parent)
        {
            View v = convertView;
            final int listPosition = position;

            if(v == null)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                v = layoutInflater.inflate(R.layout.teacher_attendance_item, null);
            }

            final Attendance attendance = getItem(position);
            TextView studentName = (TextView) v.findViewById(R.id.txtStudentName);
            List<String> status = new ArrayList<>();
            status.add("P");
            status.add("A");
            status.add("E");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, R.layout.my_spinner, status);
            final Spinner statusSpinner = (Spinner) v.findViewById(R.id.statusSpinner);
            dataAdapter.setDropDownViewResource(R.layout.my_spinner);
            statusSpinner.setAdapter(dataAdapter);

            /***SETS VALUE OF SPINNER INTO ATTENDANCE**/
            statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    attendanceList.get(listPosition).setStatus(adapterView.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    attendanceList.get(listPosition).setStatus("P");

                }
            });

            if(attendance != null)
            {
                studentName.setText(attendance.getStudent().getStudentLastName() + " " +
                        attendance.getStudent().getStudentFirstName());

                /***LOADS ATTENDANCE STATUS VALUE INTO SPINNER**/
                String attendanceStatus = attendanceList.get(listPosition).getStatus();
                if(attendanceStatus != null){
                    int attendanceStatusVal = dataAdapter.getPosition(attendanceStatus);
                    statusSpinner.setSelection(attendanceStatusVal);
                }
            }

            return v;
        }
    }
    public void updateAttendanceList(List<Attendance> attendanceList)
    {

        if (attendanceList.size() < 1) {
            TextView noAttendance = (TextView)findViewById(R.id.noAttendance);
            noAttendance.setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        for(Attendance a : attendanceList)
        {
            this.attendanceList.add(a);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.TEACHER_ATTENDANCE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
    /***
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
             *
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
            TextView date = (TextView) getActivity().findViewById(R.id.date);

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = new Date(cal.getTimeInMillis());
                    //cal.getTime();

            // Format the date using style and locale
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(chosenDate);

            // Display the chosen date to app interface
            date.setText(formattedDate);
            dateEntered = formattedDate;
        }
    }
     ***/
    public void onResume(){
        super.onResume();
        if(courseID == 0)
        {
            Intent intent = ViewCoursesTeacherActivity.createIntent(this);
            intent.putExtra("actionType", 2);
            this.startActivity(intent);
        }
    }

    public void onPause(){
        super.onPause();
        courseID = 0;
    }
}

