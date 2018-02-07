package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.OrbitUserPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewCourseAssignmentsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    StudentService studentService = new StudentService(this);
    private static int courseID = 0;

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, ViewCourseAssignmentsActivity.class);
        ViewCourseAssignmentsActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_course_assignments, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewCourseAssignmentsActivity.Adapter(this));

        FloatingActionButton mFabAddCourse = (FloatingActionButton) findViewById(R.id.fab_add_assignment);
        mFabAddCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CreateAssignment", "We want to add a new Assignment.");
                Intent chooseCourseActivity = new Intent(ViewCourseAssignmentsActivity.this, CreateAssignmentActivity.class);
                ViewCourseAssignmentsActivity.this.startActivity(chooseCourseActivity);
            }

        });

        //studentService.findAllStudents(this);
    }

    /*private void enrollStudents()
    {
        List<Student> enrollList = new ArrayList<>();
        for(int i = 0; i < ViewCourseAssignmentsActivity.Adapter.assignments.size(); i++)
        {
            if(ViewCourseAssignmentsActivity.Adapter.assignments.get(i).getIsSelected())
                enrollList.add(ViewCourseAssignmentsActivity.Adapter.assignments.get(i));
        }

        studentService.enrollStudentsInCourse(enrollList, 1);
    }*/

    /*public void saveStudentList()
    {
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(getApplicationContext());
        orbitPref.storeUserPreference("studentList", ViewCourseAssignmentsActivity.Adapter.assignments);
    }*/

    public void updateAssignmentList(List<Assignment> assignmentList)
    {
        for(Assignment a : assignmentList)
        {
            ViewCourseAssignmentsActivity.Adapter.assignments.add(a);
        }

        reloadList();
    }

    public void reloadList()
    {
        //saveStudentList();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewCourseAssignmentsActivity.ViewHolder> {

        private final Activity context;
        private static List<Assignment> assignments = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            assignments.clear();

            /*if(students.size() <= 0) {
                //inital seed of list if needed
            }*/
        }

        public static void addAssignment(Assignment assignment)
        {
            assignments.add(assignment);
        }

        @Override
        public ViewCourseAssignmentsActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.assignment_item, parent, false);
            return new ViewCourseAssignmentsActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewCourseAssignmentsActivity.ViewHolder holder, int position) {
            Assignment assignment = assignments.get(position);

            holder.txtAssignmentName.setText(assignment.getName());

            if(assignment.getIsSelected())
                holder.itemView.setBackgroundColor(Color.parseColor("#90CAF9"));
            else
                holder.itemView.setBackgroundColor(Color.WHITE);
        }

        @Override
        public int getItemCount() {
            return assignments.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final ImageView image;
        public final TextView txtAssignmentName;
        public boolean isSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            image = (ImageView) itemView.findViewById(R.drawable.ic_description_white_24dp);
            txtAssignmentName = (TextView) itemView.findViewById(R.id.txtAssignmentName);
            isSelected = false;
        }

        @Override
        public void onClick(View v) {
            /*int position = getAdapterPosition();
            if(ViewCourseAssignmentsActivity.Adapter.assignments.get(position).getIsSelected()) {
                ViewCourseAssignmentsActivity.Adapter.assignments.get(position).setIsSelected(false);
                itemView.setBackgroundColor(Color.WHITE);
            }
            else {
                ViewCourseAssignmentsActivity.Adapter.assignments.get(position).setIsSelected(true);
                itemView.setBackgroundColor(Color.parseColor("#90CAF9"));
            }*/

        }

        @Override
        public boolean onLongClick(View v) {
            /*int position = getAdapterPosition();
            String top = Adapter.memes.get(position).getTxtTop();
            String bottom = Adapter.memes.get(position).getTxtBottom();
            String url = Adapter.memes.get(position).getMemeURL();

            Context context = itemView.getContext();
            int TEST = 0;

            context.startActivity(EditMeme.createIntent(
                    context, Adapter.memes, position, itemView, top, bottom, url));*/

            return false;
        }

    }

}

