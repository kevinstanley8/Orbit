package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Course;
import net.orbit.orbit.services.CourseService;
import net.orbit.orbit.utils.OrbitUserPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChooseCourseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    CourseService courseService = new CourseService(this);

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, ChooseCourseActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_choose_course, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChooseCourseActivity.Adapter(this));

        findViewById(R.id.btnAssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignCourses();
            }
        });

        if(ChooseCourseActivity.Adapter.courses.size() == 0){
            courseService.getAllCourses(this);

        }
    }

    private void assignCourses()
    {
        List<Course> assignList = new ArrayList<>();
        for(Course c : ChooseCourseActivity.Adapter.courses){
            if(c.getIsSelected()){
                assignList.add(c);
            }
        }

        // courseService.assignCourseToTeacher(assignList, 1);
    }

    public void updateCourseList(List<Course> courseList)
    {
        ChooseCourseActivity.Adapter.courses = new ArrayList<>();
        for(Course c : courseList){
            ChooseCourseActivity.Adapter.courses.add(c);

        }
        reloadList();
    }

    public void reloadList()
    {
        saveCourseList();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void saveCourseList()
    {
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(getApplicationContext());
        orbitPref.storeUserPreference("courseList", ChooseCourseActivity.Adapter.courses);
    }

    public void loadList()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Course>>() {}.getType();
        List<Course> savedCourseList = new ArrayList<>();
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(getApplicationContext());
        savedCourseList = gson.fromJson(orbitPref.getUserPreference("courseList"), type);

        //only set the course list if a List was found saved in Shared Preferences
        if(savedCourseList != null && savedCourseList.size() > 0) {
            ChooseCourseActivity.Adapter.courses = savedCourseList;
        }
    }

    public static class Adapter extends RecyclerView.Adapter<ChooseCourseActivity.ViewHolder> {

        private final Activity context;
        private static List<Course> courses = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            courses.clear();
        }

        public static void addCourse(Course course)
        {
            courses.add(course);
        }

        @Override
        public ChooseCourseActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.course_item, parent, false);
            return new ChooseCourseActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChooseCourseActivity.ViewHolder holder, int position) {
            Course course = courses.get(position);

            holder.txtCourseName.setText(course.getName());

            if(course.getIsSelected())
                holder.itemView.setBackgroundColor(Color.parseColor("#90CAF9"));
            else
                holder.itemView.setBackgroundColor(Color.WHITE);

            //set created text info section
            // StringBuilder sb = new StringBuilder();

            //String testImage = "http://media2.s-nbcnews.com/j/streams/2013/june/130617/6c7911377-tdy-130617-leo-toasts-1.nbcnews-ux-2880-1000.jpg";
            //Glide.with(context).load(testImage).into(holder.memeImage);
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final ImageView iconImage;
        public final TextView txtCourseName;
        public boolean isSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            iconImage = (ImageView) itemView.findViewById(R.drawable.ic_class_black_24px);
            txtCourseName = (TextView) itemView.findViewById(R.id.txtCourseName);
            isSelected = false;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(ChooseCourseActivity.Adapter.courses.get(position).getIsSelected()) {
                ChooseCourseActivity.Adapter.courses.get(position).setIsSelected(false);
                itemView.setBackgroundColor(Color.WHITE);
            }
            else {
                ChooseCourseActivity.Adapter.courses.get(position).setIsSelected(true);
                itemView.setBackgroundColor(Color.parseColor("#90CAF9"));
            }
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
