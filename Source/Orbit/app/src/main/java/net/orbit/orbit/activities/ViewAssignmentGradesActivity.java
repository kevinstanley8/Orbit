package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.GradeService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignmentGradesActivity extends BaseActivity {
    public static int assignmentID = 0;
    private ListView listView;

    Context context;
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
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_assignment_grades, relativeLayout);

        listView = (ListView) findViewById(R.id.recyclerView);
        ListAdapter customAdapter = new ListAdapter(this, R.layout.grade_item, gradeList);
        listView.setAdapter(customAdapter);

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

        listView.setVisibility(View.VISIBLE);
        for(Grade g : gradeList)
        {
            this.gradeList.add(g);
        }
    }

    public void saveGrades()
    {
        View childView;
        EditText gradeValue;
        SaveGradesDTO saveGradesDTO = new SaveGradesDTO();

        for(int i = 0; i < listView.getChildCount(); i++)
        {
            childView = listView.getChildAt(i);
            gradeValue = (EditText) childView.findViewById(R.id.txtGrade);
            this.gradeList.get(i).setGrade(gradeValue.getText().toString());
            this.gradeList.get(i).getAssignment().setAssignmentId(ViewAssignmentGradesActivity.assignmentID);
            saveGradesDTO.addGrade(this.gradeList.get(i));
        }
        GradeService gradeService = new GradeService(this);
        gradeService.saveGrades(saveGradesDTO);
    }

    public class ListAdapter extends ArrayAdapter<Grade>
    {
        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Grade> grades) {
            super(context, resource, grades);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            final int holder = position;

            if(v == null)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                v = layoutInflater.inflate(R.layout.grade_item, null);
            }

            Grade grade = getItem(position);
            TextView studentName = (TextView) v.findViewById(R.id.txtStudentName);
            final EditText gradeValue = (EditText) v.findViewById(R.id.txtGrade);

            if(grade != null)
            {
                studentName.setText(grade.getStudent().getStudentLastName() + " " +
                                    grade.getStudent().getStudentFirstName());
                gradeValue.setText(grade.getGrade());
            }

            gradeValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    gradeList.get(holder).setGrade(charSequence.toString());

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    gradeList.get(holder).setGrade(editable.toString());
                }
            });
            return v;
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