package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        EditText nameInput = (EditText) findViewById(R.id.edit_txt_name);
        name = nameInput.getText().toString();

        EditText emailInput = (EditText) findViewById(R.id.edit_txt_emailAddress);
        email = emailInput.getText().toString();

        EditText passwordInput = (EditText) findViewById(R.id.edit_txt_password);
        password = passwordInput.getText().toString();

        StudentData.clearAllData();
        StudentData.setName(name);
        StudentData.setEmail(email);
        StudentData.setPassword(password);
        StudentData.setCurrentcourse(-1);
        StudentData.setCourses(courses);

        // after clicking the register button, place the data into an object in the database
    }
}
