package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Attendance;
import net.orbit.orbit.services.AttendanceService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4/6/2018.
 */

public class ViewStudentCourseAttendanceActivity extends BaseActivity {
    private static int courseID, studentID;
    private Context context;
    private RecyclerView recyclerView;
    private List<Attendance> attendanceList = new ArrayList<>();

    public static Intent createIntent(Context context, int studentID, int courseID) {
        Intent i = new Intent(context, ViewStudentCourseAttendanceActivity.class);
        ViewStudentCourseAttendanceActivity.courseID = courseID;
        ViewStudentCourseAttendanceActivity.studentID = studentID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_course_grades, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewStudentCourseAttendanceActivity.Adapter(this));

        AttendanceService attendanceService = new AttendanceService(this);
        attendanceService.getStudentCourseAttendance(this, studentID, courseID);
    }

    public void updateAttendanceList(List<Attendance> attendanceList) {
        for (Attendance a : attendanceList) {
            ViewStudentCourseAttendanceActivity.Adapter.attendanceList.add(a);
        }

        reloadList();
    }

    public void reloadList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewStudentCourseAttendanceActivity.ViewHolder> {

        private final Activity context;
        private static List<Attendance> attendanceList = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            attendanceList.clear();
        }

        @Override
        public ViewStudentCourseAttendanceActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.student_attendance_item, parent, false);
            return new ViewStudentCourseAttendanceActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewStudentCourseAttendanceActivity.ViewHolder holder, int position) {
            Attendance a = attendanceList.get(position);

            holder.txtDate.setText(a.getDate().toString());
            holder.txtAttendanceStatus.setText(a.getStatus());

        }

        @Override
        public int getItemCount() {
            return attendanceList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView txtDate;
        public final TextView txtAttendanceStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtDate = (TextView) itemView.findViewById(R.id.txtDateItem);
            txtAttendanceStatus = (TextView) itemView.findViewById(R.id.status);
        }

        @Override
        public void onClick(View v) {

        }
        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.STUDENT_ATTENDANCE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
