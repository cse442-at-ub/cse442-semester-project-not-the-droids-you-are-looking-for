package com.example.kiwiboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfCoursesFragment extends Fragment {
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View profView = inflater.inflate(R.layout.fragment_prof_courses, container, false);

        ArrayList<Course> courses;
        String[] courseNames;
        int numCourses;

        // Get professors active courses logged
        courses = ProfData.getCourses();
        if (courses == null){
            return profView;
        }
        numCourses = courses.size();

        // Fill course names to display

        courseNames = new String[numCourses];
        Course c;
        for (int index = 0; index < numCourses ;index++){
            c = courses.get(index);
            courseNames[index] = c.getCourseName();
        }

        // Identify the list view necessary in xml
        ListView proflist = profView.findViewById(R.id.lstProfCourses);

        // Populate the list view using the adapter
        ArrayAdapter<String> profCoursesAdapter = new ArrayAdapter<String>(profView.getContext(),
                R.layout.course_item_format,
                courseNames);

        //populate the listview

        proflist.setAdapter(profCoursesAdapter);

        // On click listener
        toolbar = (Toolbar) profView.findViewById(R.id.professor_toolbar);
        proflist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ProfData.setCurrentcourse(arg2);
                // Set toolbar text
                String coursetext;
                int currentcourse = ProfData.getCurrentcourse();
                if (currentcourse >=0){
                    coursetext = ProfData.getCourses().get(currentcourse).getCourseName();
                } else {
                    coursetext = "";
                }
                ProfMain profMain = (ProfMain) getActivity();
                profMain.setToolbarText(coursetext);
                profMain.setDrawerCourse(coursetext);
                profMain.switchToHome();
            }
        });


        return profView;
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
