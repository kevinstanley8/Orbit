package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.Course;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.AssignmentService;
import net.orbit.orbit.services.GradeService;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignmentGradesActivity extends BaseActivity {
    public static int assignmentID = 0;
    private RecyclerView recyclerView;
    private static int courseID = 0;
    private List<Grade> gradeList = new ArrayList<>();

    public static Intent createIntent(Context context, int courseID, int assignmentID) {
        Intent i = new Intent(context, ViewAssignmentGradesActivity.class);
        ViewAssignmentGradesActivity.courseID = courseID;
        ViewAssignmentGradesActivity.assignmentID = assignmentID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_assignment_grades, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewAssignmentGradesActivity.Adapter(this));


        findViewById(R.id.btnSaveGrades).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGrades();
            }
        });

        GetGradesForAssignmentDTO getGradesForAssignmentDTO = new GetGradesForAssignmentDTO(ViewAssignmentGradesActivity.courseID, ViewAssignmentGradesActivity.assignmentID);
        GradeService gradeService = new GradeService(this);
        gradeService.getAllStudentGradesForAssignment(this, getGradesForAssignmentDTO);
    }

    public void updateGradeList(List<Grade> gradeList)
    {

        if (gradeList.size() < 1) {
            TextView noGrades = (TextView)findViewById(R.id.noGrades);
            noGrades.setVisibility(View.VISIBLE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        for(Grade g : gradeList)
        {
            /*if(g.getAssignment().getCourse() == null)
                g.getAssignment().setCourse(new Course());*/
            ViewAssignmentGradesActivity.Adapter.grades.add(g);
            this.gradeList.add(g);
        }

        reloadList();
    }

    public void saveGrades()
    {
        //***** figure out why Grade.toString() is giving a null pointer
        ArrayList<EditText> myEditTextList = new ArrayList<EditText>();

        for( int i = 0; i < recyclerView.getFocusables(View.FOCUS_FORWARD).size(); i++ )
            if( recyclerView.getFocusables(View.FOCUS_FORWARD).get(i) instanceof EditText )
                myEditTextList.add( (EditText) recyclerView.getFocusables(View.FOCUS_FORWARD).get(i) );

        SaveGradesDTO saveGradesDTO = new SaveGradesDTO();
        int index = 0;
        for(int j = 0; j < ViewAssignmentGradesActivity.Adapter.grades.size(); j++)
        {
            //get the grade from the screen and save it
            ((Grade)this.gradeList.get(j)).setGrade(myEditTextList.get(index).getText().toString());
            ((Grade)this.gradeList.get(j)).getAssignment().setAssignmentId(ViewAssignmentGradesActivity.assignmentID);
            saveGradesDTO.addGrade((Grade)this.gradeList.get(j));
            index++;
        }
        GradeService gradeService = new GradeService(this);
        gradeService.saveGrades(saveGradesDTO);
    }

    public void reloadList()
    {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewAssignmentGradesActivity.ViewHolder>
    {

        private final Activity context;
        private static List<Grade> grades = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            grades.clear();
        }

        public static void addGrade(Grade grade)
        {
            grades.add(grade);
        }

        @Override
        public ViewAssignmentGradesActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = context.getLayoutInflater().inflate(R.layout.grade_item, parent, false);
            return new ViewAssignmentGradesActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewAssignmentGradesActivity.ViewHolder holder, int position) {
            Grade grade = grades.get(position);

            holder.txtStudentName.setText(grade.getStudent().getStudentLastName() + ", " + grade.getStudent().getStudentFirstName());
            holder.txtGrade.setText(grade.getGrade());
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        public final TextView txtStudentName;
        public boolean isSelected;
        public EditText txtGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            //image = (ImageView) itemView.findViewById(R.drawable.ic_description_black_24dp);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            txtGrade = (EditText) itemView.findViewById(R.id.txtGrade);
            isSelected = false;
        }

        @Override
        public void onClick(View v) {
            /*int position = getAdapterPosition();
            int gradeID = ViewAssignmentGradesActivity.Adapter.grades.get(position).getGradeId();

            Context context = itemView.getContext();
            Intent intent = ViewAssignmentGradesActivity.createIntent(context, gradeID);
            context.startActivity(intent);*/

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
    public void onResume()
    {
        for( int i = 0; i < recyclerView.getFocusables(View.FOCUS_FORWARD).size(); i++ )
            if( recyclerView.getFocusables(View.FOCUS_FORWARD).get(i) instanceof EditText )
                ((EditText) recyclerView.getFocusables(View.FOCUS_FORWARD).get(i)).setText(gradeList.get(i).getGrade());
        super.onResume();
    }

}
