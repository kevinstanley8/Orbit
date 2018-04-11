package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import net.orbit.orbit.models.dto.SaveGradesDTO;
import net.orbit.orbit.models.pojo.Conduct;
import net.orbit.orbit.models.pojo.CourseGrade;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.ConductService;
import net.orbit.orbit.services.GradeService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewMyConductActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static int studentID = 0;

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, ViewMyConductActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if student ID has not been set then the user must select a student to view conduct
        int id = getIntent().getIntExtra("chosenStudentID", -1);
        context = this;

        if(id == -1)
        {
            Intent intent = ChooseStudentActivity.createIntent(this);
            intent.putExtra("actionType", 1);
            this.startActivity(intent);
        }
        else
            ViewMyConductActivity.studentID = id;

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_my_conduct, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewMyConductActivity.Adapter(this));

        TextView txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setText(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString());

        ConductService conductService = new ConductService(this);
        conductService.getStudentConduct(this, studentID);
    }

    public void updateConductList(List<Conduct> conductList)
    {
        for(Conduct c : conductList)
        {
            ViewMyConductActivity.Adapter.conducts.add(c);
        }

        reloadList();
    }

    public void reloadList()
    {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewMyConductActivity.ViewHolder> {

        private final Activity context;
        private static List<Conduct> conducts = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            conducts.clear();
        }

        public static void addGrade(Conduct conduct)
        {
            conducts.add(conduct);
        }

        @Override
        public ViewMyConductActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.view_conduct_item, parent, false);
            return new ViewMyConductActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewMyConductActivity.ViewHolder holder, int position) {
            Conduct conduct = conducts.get(position);

            holder.txtCourseName.setText(conduct.getCourse().getName());
            holder.rating.setRating(conduct.getScore());
        }

        @Override
        public int getItemCount() {
            return conducts.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView txtCourseName;
        public final RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int courseID = ViewMyConductActivity.Adapter.conducts.get(position).getCourse().getCourseId();

            Context context = itemView.getContext();
            Intent intent = ViewMyConductCourseActivity.createIntent(context, ViewMyConductActivity.studentID, courseID);
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
                p.showPopup(PopupMessages.STUDENT_CONDUCT_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }


}
