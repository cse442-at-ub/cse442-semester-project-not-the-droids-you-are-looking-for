package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void studentMode(View view){
        this.finish();
        StudentData.setStudentmode(true);

        // Load in sample courses for the student
        SampleData loader = new SampleData();
        loader.loadStudentCourses();
        if (StudentData.getEmail() == null || StudentData.getEmail().equals(""))
            loader.loadStudentInfo();
        // Set question 14 and 15 to active for students in course 0 (Geology)
        Course course = StudentData.getCourse(0);
        Question question = course.getQuestion(13);
        question.setActive(true);
        course.setQuestion(13, question);
        question = course.getQuestion(14);
        question.setActive(true);
        course.setQuestion(14, question);
        StudentData.setCourse(0, course);

        StudentData.setCurrentcourse(0);

        startActivity(new Intent(Mode.this, StudentMain.class));
    }

    public void professorMode(View view){
        this.finish();
        ProfData.setProfessormode(true);

        // Load in sample courses for the professor
        SampleData loader = new SampleData();
        loader.loadProfCourses();
        if (ProfData.getEmail() == null || ProfData.getEmail().equals(""))
            loader.loadProfInfo();
        ProfData.setCurrentcourse(0);

        startActivity(new Intent(Mode.this, ProfMain.class));
    }
}
