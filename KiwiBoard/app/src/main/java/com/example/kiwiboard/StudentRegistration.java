package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentRegistration extends AppCompatActivity {
    private String name;
    private String email;
    private String password;
    private ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Button registerButton = (Button) findViewById(R.id.register_button);
        TextView hasAccount = (TextView) findViewById(R.id.txt_hasAccount);
        TextView professorLogin = (TextView) findViewById(R.id.txt_profLogin);

        EditText nameInput = (EditText) findViewById(R.id.edit_txt_name);
        name = nameInput.getText().toString();

        EditText emailInput = (EditText) findViewById(R.id.edit_txt_emailAddress);
        email = emailInput.getText().toString();

        EditText passwordInput = (EditText) findViewById(R.id.edit_txt_password);
        password = passwordInput.getText().toString();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                startActivity(new Intent(StudentRegistration.this, StudentMain.class));
            }
        });

        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentRegistration.this, Login.class));
            }
        });

        // Need max's code
        professorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    startActivity(new Intent(StudentRegistration.this, ProfessorRegistration.class));
            }
        });
    }

    public void createUser() {
        StudentData.clearAllData();
        StudentData.setName(name);
        StudentData.setEmail(email);
        StudentData.setPassword(password);
        StudentData.setCurrentcourse(-1);
        StudentData.setCourses(courses);
    }
}
