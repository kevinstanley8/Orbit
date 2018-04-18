package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Attendance;
import net.orbit.orbit.services.AttendanceService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyAttendanceActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static int studentID = 0;

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, MyAttendanceActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if student ID has not been set then the user must select a student to view Attendance
        int id = getIntent().getIntExtra("chosenStudentID", -1);
        context = this;

        if(id == -1)
        {
            Intent intent = ChooseStudentActivity.createIntent(this);
            intent.putExtra("actionType", 2);
            this.startActivity(intent);
        }
        else
            MyAttendanceActivity.studentID = id;

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_attendance_course_list, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAttendanceActivity.Adapter(this));

        TextView txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setText(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString());

        AttendanceService AttendanceService = new AttendanceService(this);
        AttendanceService.getStudentAttendance(this, studentID);
    }

    public void updateAttendanceList(List<Attendance> AttendanceList)
    {
        for(Attendance c : AttendanceList)
        {
            MyAttendanceActivity.Adapter.Attendances.add(c);
        }

        reloadList();
    }

    public void reloadList()
    {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<MyAttendanceActivity.ViewHolder> {

        private final Activity context;
        private static List<Attendance> Attendances = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            Attendances.clear();
        }

        public static void addGrade(Attendance Attendance)
        {
            Attendances.add(Attendance);
        }

        @Override
        public MyAttendanceActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.course_attendance_item, parent, false);
            return new MyAttendanceActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAttendanceActivity.ViewHolder holder, int position) {
            Attendance Attendance = Attendances.get(position);

            holder.txtCourseName.setText(Attendance.getCourse().getName());
            Log.d("CHEESEBURGER", String.valueOf(Attendance.getCourse().getName()));
            holder.status.setText(Attendance.getStatus());

        }

        @Override
        public int getItemCount() {
            return Attendances.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView txtCourseName;
        public final TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            status = (TextView) itemView.findViewById(R.id.txtAttendanceStatus);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int courseID = MyAttendanceActivity.Adapter.Attendances.get(position).getCourse().getCourseId();

            Context context = itemView.getContext();
            Intent intent = ViewStudentCourseAttendanceActivity.createIntent(context, MyAttendanceActivity.studentID, courseID);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.COURSE_STUDENT_ATTENDANCE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }


}