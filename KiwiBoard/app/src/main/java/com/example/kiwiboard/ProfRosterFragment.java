package com.example.kiwiboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfRosterFragment extends Fragment {
    private ArrayList<Student>students;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rosterview = inflater.inflate(R.layout.fragment_prof_roster,container,false);

        // Declare variables
        int courseindex = ProfData.getCurrentcourse();

        // Check if there is any course
        if (courseindex < 0){
            return rosterview;
        }

        // Get the current course
        Course currentcourse = ProfData.getCourses().get(courseindex);
        students = currentcourse.getStudents();
        final int numStudents = students.size();
        String name;

        // Display number of students to textview
        TextView stuTotal=(TextView)rosterview.findViewById(R.id.txtpStuTotal);
        stuTotal.setText(" " + numStudents);

        // Populate String array of student names
        String[] studentArray = new String[numStudents];
        for (int i = 0; i < numStudents; i++) {
            name = students.get(i).getName();
            studentArray[i] = name;
        }

        roster(rosterview, studentArray);

        return rosterview;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Variable initializations here, excluding View objects
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Initialise View objects here
    }

    public void roster(View rosterview, String[] studentArray){
        // Identify List View in fragment
        ListView studentlist = rosterview.findViewById(R.id.lstprof_roster);

        // ListView Array Adapter
        ArrayAdapter<String> rosterViewAdapter = new ArrayAdapter<String>(
                rosterview.getContext(),
                android.R.layout.simple_list_item_1,
                studentArray
        );

        studentlist.setAdapter(rosterViewAdapter);
    }

}