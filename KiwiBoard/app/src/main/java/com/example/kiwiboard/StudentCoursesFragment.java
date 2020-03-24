package com.example.kiwiboard;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class StudentCoursesFragment extends Fragment {

    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View studentView = inflater.inflate(R.layout.fragment_student_courses,container,false);

        // Get the courses of the student
        ArrayList<Course> courses = StudentData.getCourses();
        int numCourses = courses.size();

        // Create and populate a primitive array of course names
        String[] coursenames = new String[numCourses];
        Course course;
        for (int index = 0; index < numCourses; index++ ){
            course = courses.get(index);
            coursenames[index] = course.getCourseName();
        }

        // Identify the List View in the courses fragment
        ListView studentlist = studentView.findViewById(R.id.lstStudentCourses);

        // Populate the list view adapter
        ArrayAdapter<String> studentCoursesAdapter = new ArrayAdapter<String>(
                studentView.getContext(),
                R.layout.course_item_format,
                coursenames
        );

        // Populate the list view using the adapter
        studentlist.setAdapter(studentCoursesAdapter);

        // On click listener
        toolbar = (Toolbar) studentView.findViewById(R.id.student_toolbar);
        studentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                StudentData.setCurrentcourse(arg2);
                // Set toolbar text
                String coursetext;
                int currentcourse = StudentData.getCurrentcourse();
                if (currentcourse >=0){
                    coursetext = StudentData.getCourses().get(currentcourse).getCourseName();
                } else {
                    coursetext = "";
                }
                StudentMain studentmain = (StudentMain) getActivity();
                studentmain.setToolbarText(coursetext);
                studentmain.setDrawerCourse(coursetext);
            }
        });

        return studentView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Variable initializations here, excluding View objects
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Initialise View objects here
    }

}


/*
    ListView list = (ListView) findViewById(R.id.list);
adapter = new MyAdapter(this,R.layout.main,
        R.id.row_text,
        new String[]{"Uno", "Dos", "Tres"}  );
        list.setAdapter(adapter);

//Custom Adapter class

class MyAdapter extends ArrayAdapter<String>{

    String[] mStrings;
    LayoutInflater mInflater;

    public MyAdapter(Context context, int resource, int textViewResourceId,
                     String[] strings) {
        super(context, resource, textViewResourceId, strings);
        mStrings = strings;
        mInflater = (LayoutInflater) FirstAct.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = mInflater.inflate(R.layout.activity_student_main,null, false);

        TextView text = (TextView) view.findViewById(R.id.row_text);
        text.setText(mStrings[position]);

        Spannable str = (Spannable) text.getText();
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return view;
    }
}

*/
