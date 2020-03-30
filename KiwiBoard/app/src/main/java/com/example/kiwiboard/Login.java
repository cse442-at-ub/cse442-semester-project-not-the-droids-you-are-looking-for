package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void loginPassthrough(View view){
        this.finish();
        startActivity(new Intent(Login.this, Mode.class));
    }

    public void forgotPW(View view){
        this.finish();
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

    public void StudentRegistration(View view){
        this.finish();
        startActivity(new Intent(Login.this, StudentRegistration.class));
    }

    public void ProfessorRegistration(View view){
        this.finish();
        startActivity(new Intent(Login.this, ProfRegistration.class));
    }

}