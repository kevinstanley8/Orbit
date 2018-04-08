package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.dto.GetGradesForAssignmentDTO;
import net.orbit.orbit.models.dto.SaveConductDTO;
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Conduct;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.ConductService;
import net.orbit.orbit.services.GradeService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConductActivity extends BaseActivity {
    public static int assignmentID = 0;
    private ListView listView;

    Context context;
    private static int courseID = 0;
    private List<Conduct> conductList = new ArrayList<>();

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, ConductActivity.class);
        ConductActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        // verify course has been chosen
        if(courseID == 0)
        {
            Intent intent = ViewCoursesTeacherActivity.createIntent(this);
            intent.putExtra("actionType", 1);
            this.startActivity(intent);
        }

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_conduct, relativeLayout);

        TextView txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setText(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString());

        listView = (ListView) findViewById(R.id.recyclerView);
        ConductActivity.ListAdapter customAdapter = new ConductActivity.ListAdapter(this, R.layout.grade_item, conductList);
        listView.setAdapter(customAdapter);

        findViewById(R.id.btnSaveConduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConduct();
            }
        });

        ConductService conductService = new ConductService(this);
        conductService.getCourseConduct(this, courseID);
    }

    public void updateConductList(List<Conduct> conductList) {
        if (conductList.size() < 1) {
            TextView noConduct = (TextView)findViewById(R.id.noConduct);
            noConduct.setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        for(Conduct c : conductList)
        {
            this.conductList.add(c);
        }
    }

    public void saveConduct() {
        View childView;
        RatingBar rating;
        SaveConductDTO saveConductDTO = new SaveConductDTO();

        for(int i = 0; i < listView.getChildCount(); i++)
        {
            childView = listView.getChildAt(i);
            rating = (RatingBar) childView.findViewById(R.id.rating);

            if(rating.getRating() <= 0)
                continue;

            Conduct conduct = this.conductList.get(i);
            java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            //conduct.setDate(currentDate);
            conduct.setScore((int)rating.getRating());
            saveConductDTO.addConduct(conduct);
        }
        ConductService conductService = new ConductService(this);
        conductService.saveConduct(saveConductDTO);
    }

    public class ListAdapter extends ArrayAdapter<Conduct> {
        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Conduct> conducts) {
            super(context, resource, conducts);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final int holder = position;

            if(v == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                v = layoutInflater.inflate(R.layout.conduct_item, null);
            }

            Conduct conduct = getItem(position);
            TextView studentName = (TextView) v.findViewById(R.id.txtStudentName);
            final RatingBar rating = (RatingBar) v.findViewById(R.id.rating);

            if(conduct != null) {
                studentName.setText(conduct.getStudent().getStudentLastName() + ", " +
                        conduct.getStudent().getStudentFirstName());
                rating.setRating(conduct.getScore());
            }

            /*gradeValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //conductList.get(holder).setScore(charSequence.toString());

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //gradeList.get(holder).setGrade(editable.toString());
                }
            });*/
            return v;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.VIEW_CONDUCT_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
