package net.orbit.orbit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.Student;
import net.orbit.orbit.services.PopupService;
import net.orbit.orbit.services.StudentService;
import net.orbit.orbit.utils.OrbitUserPreferences;
import net.orbit.orbit.utils.PopupMessages;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EnrollStudentInCourseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static int courseID = 0;

    public static Intent createIntent(Context context, int courseID) {
        Intent i = new Intent(context, EnrollStudentInCourseActivity.class);
        EnrollStudentInCourseActivity.courseID = courseID;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //need to inflate this activity inside the relativeLayout inherited from BaseActivity.  This will add this view to the mainContent layout
        getLayoutInflater().inflate(R.layout.activity_enroll_student_in_course, relativeLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EnrollStudentInCourseActivity.Adapter(this));
        findViewById(R.id.btnEnroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollStudents();
            }
        });

        //loadList();

        //if(EnrollStudentInCourseActivity.Adapter.students == null || EnrollStudentInCourseActivity.Adapter.students.size() < 0)

        StudentService studentService = new StudentService(this);
        studentService.findAllStudents(this);
    }

    private void enrollStudents()
    {
        List<Student> enrollList = new ArrayList<>();
        for(Student s : EnrollStudentInCourseActivity.Adapter.students)
        {
            if(s.getIsSelected())
                enrollList.add(s);
        }
        if (enrollList.size() < 1) {
            Toast.makeText(this, "Please select at least one student!" , Toast.LENGTH_SHORT).show();
            return;
        }
        StudentService studentService = new StudentService(this);
        studentService.enrollStudentsInCourse(enrollList, EnrollStudentInCourseActivity.courseID);
    }

    public void saveStudentList()
    {

        OrbitUserPreferences orbitPref = new OrbitUserPreferences(this);
        orbitPref.storeListPreference("studentList", EnrollStudentInCourseActivity.Adapter.students);
    }

    public void updateStudentList(List<Student> studentList)
    {

        if (studentList.size() < 1) {
            TextView noStudents = (TextView)findViewById(R.id.noStudents);
            noStudents.setVisibility(View.VISIBLE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);

        for(Student s : studentList)
        {
            EnrollStudentInCourseActivity.Adapter.students.add(s);
        }

        reloadList();
    }

    public void reloadList()
    {
        saveStudentList();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadList()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>() {}.getType();
        List<Student> savedStudentList = new ArrayList<>();
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(this);
        savedStudentList = gson.fromJson(orbitPref.getStringPreference("studentList"), type);

        //only set the meme list if a List was found saved in Shared Preferences
        if(savedStudentList != null && savedStudentList.size() > 0) {
            EnrollStudentInCourseActivity.Adapter.students = savedStudentList;
        }
    }

    public static class Adapter extends RecyclerView.Adapter<EnrollStudentInCourseActivity.ViewHolder> {

        private final Activity context;
        private static List<Student> students = new ArrayList<>();

        public Adapter(Activity context) {
            this.context = context;
            students.clear();

            /*if(students.size() <= 0) {
                //inital seed of list if needed
            }*/
        }

        public static void addStudent(Student student)
        {
            students.add(student);
        }

        @Override
        public EnrollStudentInCourseActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = context.getLayoutInflater().inflate(R.layout.student_item, parent, false);
            return new EnrollStudentInCourseActivity.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EnrollStudentInCourseActivity.ViewHolder holder, int position) {
            Student student = students.get(position);

            holder.txtStudentName.setText(student.getStudentFirstName() + " " + student.getStudentLastName());

            if(student.getIsSelected())
                holder.itemView.setBackgroundColor(Color.parseColor("#90CAF9"));
            else
                holder.itemView.setBackgroundColor(Color.WHITE);

            //set created text info section
            StringBuilder sb = new StringBuilder();

            //String testImage = "http://media2.s-nbcnews.com/j/streams/2013/june/130617/6c7911377-tdy-130617-leo-toasts-1.nbcnews-ux-2880-1000.jpg";
            //Glide.with(context).load(testImage).into(holder.memeImage);
        }

        @Override
        public int getItemCount() {
            return students.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final ImageView iconImage;
        public final TextView txtStudentName;
        public boolean isSelected;
        public Drawable itemBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            iconImage = (ImageView) itemView.findViewById(R.id.iconImage);
            iconImage.setImageResource(R.drawable.ic_perm_identity_white_24px);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            isSelected = false;
            itemBackground = txtStudentName.getBackground();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(EnrollStudentInCourseActivity.Adapter.students.get(position).getIsSelected()) {
                EnrollStudentInCourseActivity.Adapter.students.get(position).setIsSelected(false);
                txtStudentName.setBackground(itemBackground);
            }
            else {
                EnrollStudentInCourseActivity.Adapter.students.get(position).setIsSelected(true);
                txtStudentName.setBackgroundColor(Color.parseColor("#90CAF9"));
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

    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_info:
                PopupService p = new PopupService(context);
                p.showPopup(PopupMessages.ENROLL_STUDENT_IN_COURSE_MESSAGE);
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
