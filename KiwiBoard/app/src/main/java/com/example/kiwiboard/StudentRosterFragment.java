package com.example.kiwiboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class StudentRosterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rosterview = inflater.inflate(R.layout.fragment_student_roster,container,false);

        // Get the current course and declare variables
        int courseindex = StudentData.getCurrentcourse();
        Course currentcourse = StudentData.getCourses().get(courseindex);
        ArrayList<Student> students = currentcourse.getStudents();
        int numStudents = students.size();
        String name;

        // Display number of students to textview
        TextView stuTotal=(TextView)rosterview.findViewById(R.id.txtStuTotal);
        stuTotal.setText(" " + numStudents);

        // Populate String array of student names
        String[] studentArray = new String[numStudents];
        for (int i = 0; i < numStudents; i++) {
            name = students.get(i).getName();
            studentArray[i] = name;
        }

        // Identify List View in fragment
        ListView studentlist = rosterview.findViewById(R.id.lststudent_roster);

        // ListView Array Adapter
        ArrayAdapter<String> rosterViewAdapter = new ArrayAdapter<String>(
                rosterview.getContext(),
                android.R.layout.simple_list_item_1,
                studentArray
        );

        studentlist.setAdapter(rosterViewAdapter);
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

    

}