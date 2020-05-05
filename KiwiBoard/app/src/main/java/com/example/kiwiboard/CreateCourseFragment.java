package com.example.kiwiboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateCourseFragment extends Fragment {

    private EditText txtCourseName, txtAccessCode, txtClassSize, txtDescription;
    private Button btnCreateCourse;
    private String courseName, courseCode, description;
    private int classSize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_course,container,false);

        txtCourseName = rootView.findViewById(R.id.txtClassname);
        txtAccessCode = rootView.findViewById(R.id.txtAccessCode);
        txtClassSize = rootView.findViewById(R.id.txtClassSize);
        txtDescription = rootView.findViewById(R.id.txtCourseDescription);

        btnCreateCourse = rootView.findViewById(R.id.btnCreateCourse);
        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseName = txtCourseName.getText().toString();
                courseCode = txtAccessCode.getText().toString();
                //classSize = Integer.parseInt(txtClassSize.getText().toString());
                description = txtDescription.getText().toString();

                if (courseName.equals("")){
                    Toast.makeText(getActivity(), "You must enter a course name", Toast.LENGTH_SHORT).show();
                } else if(description.equals("")){
                    Toast.makeText(getActivity(), "You must enter a description", Toast.LENGTH_SHORT).show();
                } else{
                    String instructorName = ProfData.getName();
                    Course course = new Course(courseName, instructorName, description);
                    ProfData.addCourse(course);
                    Server.createCourse(getActivity(), ProfData.getID(), "p", description, courseName);
                    ProfMain profmain = (ProfMain) getActivity();
                    assert profmain != null;
                    ProfData.setCurrentcourse(ProfData.getCourses().size() - 1);
                    profmain.setToolbarText(courseName);
                    profmain.setDrawerCourse(courseName);
                    profmain.switchToHome();
                }
                //ProfMain profmain = (ProfMain) getActivity();
                //assert profmain != null;
                //profmain.createnewCourse(v);
            }
        });

        return rootView;
    }

    public void createCourse(View view){

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