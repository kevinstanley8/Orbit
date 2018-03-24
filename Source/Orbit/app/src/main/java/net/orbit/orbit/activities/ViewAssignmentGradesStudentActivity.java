package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.GradeService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignmentGradesStudentActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static int studentID = 0;
    private static int courseID = 0;
    public static PopupService p;

    public static Intent createIntent(Context context, int studentID, int courseID) {
        Intent i = new Intent(context, ViewAssignmentGradesStudentActivity.class);
        ViewAssignmentGradesStudentActivity.studentID = studentID;
        ViewAssignmentGradesStudentActivity.courseID = courseID;

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_assignment_grades_student, relativeLayout);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewAssignmentGradesStudentActivity.Adapter(this));

        p = new PopupService(this);

        GradeService gradeService = new GradeService(this);
        gradeService.getStudentCourseGrades(this, ViewAssignmentGradesStudentActivity.studentID, ViewAssignmentGradesStudentActivity.courseID);


    }

    public void updateGradeList(List<Grade> gradeList) {
        for (Grade g : gradeList) {
            ViewAssignmentGradesStudentActivity.Adapter.grades.add(g);
        }

        reloadList();
    }

    public void reloadList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewAssignmentGradesStudentActivity.ViewHolder> {

        private final Activity context;
        private static List<Grade> grades = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            grades.clear();
        }

        public static void addGrade(Grade grade) {
            grades.add(grade);
        }

        @Override
        public ViewAssignmentGradesStudentActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.course_grade_item, parent, false);
            return new ViewAssignmentGradesStudentActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewAssignmentGradesStudentActivity.ViewHolder holder, int position) {
            Grade grade = grades.get(position);

            holder.txtCourseName.setText(grade.getAssignment().getName());
            holder.txtGrade.setText(grade.getGrade() + "%");
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView txtCourseName;
        public final TextView txtGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            txtGrade = (TextView) itemView.findViewById(R.id.txtGrade);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Grade grade = ViewAssignmentGradesStudentActivity.Adapter.grades.get(position);
            Assignment assignment = grade.getAssignment();
            p.showPopup(assignment.getDescription());
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
                p.showPopup(PopupMessages.VIEW_ASSIGNMENT_GRADES_STUDENT_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
