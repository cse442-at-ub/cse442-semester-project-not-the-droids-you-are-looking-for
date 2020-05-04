package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText txtemail, txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void loginPassthrough(View view){
        finish();
        startActivity(new Intent(Login.this, Mode.class));
    }

    public void login(View view){

        txtemail = (EditText) findViewById(R.id.txtEmailInput);
        txtpassword = (EditText) findViewById(R.id.txtPasswordInput);
        String email, password;
        email = txtemail.getText().toString();
        password = txtpassword.getText().toString();

        Server.login(this, email, password);
    }

    public void launchStudent(){
        finish();
        startActivity(new Intent(this, StudentMain.class));
    }

    public void launchProfessor(){
        finish();
        startActivity(new Intent(this, ProfMain.class));
    }

    public void loginFailed(){
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    public void forgotPW(View view){
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

    public void StudentRegistration(View view){
        startActivity(new Intent(Login.this, StudentRegistration.class));
    }

    public void ProfessorRegistration(View view){
        startActivity(new Intent(Login.this, ProfRegistration.class));
    }

}