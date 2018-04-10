package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.SaveAttendanceDTO;
import net.orbit.orbit.models.pojo.Attendance;
import net.orbit.orbit.services.AttendanceService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4/6/2018.
 */

public class ViewAttendanceActivity extends BaseActivity{
    private static int courseID;
    private Context context;
    private ListView listView;
    private List<Attendance> attendanceList = new ArrayList<>();

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, ViewCourseAttendanceActivity.class);
        ViewAttendanceActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_course_attendance, relativeLayout);

        listView = (ListView) findViewById(R.id.recyclerView);
        ListAdapter customAdapter = new ListAdapter(this, R.layout.grade_item, attendanceList);
        listView.setAdapter(customAdapter);

        findViewById(R.id.btnSaveGrades).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAttendance();
            }
        });

        //GetGradesForAssignmentDTO getGradesForAssignmentDTO = new GetGradesForAssignmentDTO(ViewAssignmentGradesActivity.courseID, ViewAssignmentGradesActivity.assignmentID);
        AttendanceService attendanceService= new AttendanceService(this);
        //attendanceService.getCourseAttendance(this, courseID);
    }

    public void saveAttendance()
    {
        View childView;
        EditText comment;
        Spinner statusSpinner;
        SaveAttendanceDTO  saveAttendanceDTO = new SaveAttendanceDTO();

        for(int i = 0; i < listView.getChildCount(); i++)
        {
            childView = listView.getChildAt(i);
            comment = (EditText) childView.findViewById(R.id.txtGrade);
            statusSpinner = (Spinner)childView.findViewById(R.id.statusSpinner);
            this.attendanceList.get(i).setComment(comment.getText().toString());
            // SPINNER VALUE
            this.attendanceList.get(i).setStatus(statusSpinner.getSelectedItem().toString());
            //this.gradeList.get(i).getAssignment().setAssignmentId(ViewAssignmentGradesActivity.assignmentID);
            saveAttendanceDTO.addAttendance(this.attendanceList.get(i));
        }
        AttendanceService attendanceService = new AttendanceService(this);
        attendanceService.saveCourseAttendance(saveAttendanceDTO);
    }

    public class ListAdapter extends ArrayAdapter<Attendance>
    {
        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Attendance> grades) {
            super(context, resource, grades);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            List<String> statusList = new ArrayList<>();
            statusList.add("P");
            statusList.add("A");
            statusList.add("E");
            View v = convertView;
            final int holder = position;

            if(v == null)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                v = layoutInflater.inflate(R.layout.teacher_attendance_item, null);
            }

            final Attendance attendance = getItem(position);
            TextView studentName = (TextView) v.findViewById(R.id.txtStudentName);
            final EditText comment = (EditText) v.findViewById(R.id.comment);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, R.layout.my_spinner, statusList);
            Spinner statusSpinner = (Spinner) v.findViewById(R.id.statusSpinner);
            dataAdapter.setDropDownViewResource(R.layout.my_spinner);
            statusSpinner.setAdapter(dataAdapter);

            if(attendance != null)
            {
                studentName.setText(attendance.getStudent().getStudentLastName() + " " +
                        attendance.getStudent().getStudentFirstName());
                comment.setText(attendance.getComment());
            }

            comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    attendanceList.get(holder).setComment(charSequence.toString());

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    attendanceList.get(holder).setComment(editable.toString());
                }
            });
            return v;
        }
    }
    public void updateAttendanceList(List<Attendance> attendanceList)
    {

        if (attendanceList.size() < 1) {
            TextView noGrades = (TextView)findViewById(R.id.noGrades);
            noGrades.setVisibility(View.VISIBLE);
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
                p.showPopup(PopupMessages.VIEW_ASSIGNMENT_GRADES_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
