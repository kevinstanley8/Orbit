package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Assignment;
import net.orbit.orbit.models.pojo.Conduct;
import net.orbit.orbit.models.pojo.Grade;
import net.orbit.orbit.services.ConductService;
import net.orbit.orbit.services.GradeService;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.utils.PopupMessages;

import java.util.ArrayList;
import java.util.List;

public class ViewMyConductCourseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static int studentID = 0;
    private static int courseID = 0;
    public static PopupService p;

    public static Intent createIntent(Context context, int studentID, int courseID) {
        Intent i = new Intent(context, ViewMyConductCourseActivity.class);
        ViewMyConductCourseActivity.studentID = studentID;
        ViewMyConductCourseActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_view_my_conduct_course, relativeLayout);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewMyConductCourseActivity.Adapter(this));

        p = new PopupService(this);

        ConductService conductService = new ConductService(this);
        conductService.getStudentDailyConduct(this, ViewMyConductCourseActivity.studentID, ViewMyConductCourseActivity.courseID);
    }

    public void updateConductList(List<Conduct> conductList) {
        for (Conduct c : conductList) {
            ViewMyConductCourseActivity.Adapter.conducts.add(c);
        }

        reloadList();
    }

    public void reloadList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static class Adapter extends RecyclerView.Adapter<ViewMyConductCourseActivity.ViewHolder> {

        private final Activity context;
        private static List<Conduct> conducts = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            conducts.clear();
        }

        public static void addConduct(Conduct conduct) {
            conducts.add(conduct);
        }

        @Override
        public ViewMyConductCourseActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.view_conduct_item, parent, false);
            return new ViewMyConductCourseActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewMyConductCourseActivity.ViewHolder holder, int position) {
            Conduct conduct = conducts.get(position);

            holder.txtCourseName.setText(conduct.getDate().toString());
            holder.ratings.setRating(conduct.getScore());
        }

        @Override
        public int getItemCount() {
            return conducts.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView txtCourseName;
        public final RatingBar ratings;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            ratings = (RatingBar) itemView.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View v) {
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
                p.showPopup(PopupMessages.DAILY_CONDUCT_MESSAGE);
        }

        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
