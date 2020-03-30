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

    public void studentMode(View view){
        this.finish();
        StudentData.setStudentmode(true);
        startActivity(new Intent(Mode.this, StudentMain.class));
    }

    public void professorMode(View view){
        this.finish();
        ProfData.setProfessormode(true);
        startActivity(new Intent(Mode.this, ProfMain.class));
    }
}
