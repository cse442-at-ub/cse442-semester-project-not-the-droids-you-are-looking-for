package com.example.kiwiboard;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class StudentRegistration extends AppCompatActivity {
    private String name;
    private String email;
    private String password;
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private TextInputLayout nameTextInput;
    private TextInputLayout emailTextInput;
    private TextInputLayout passwordTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Button registerButton = (Button) findViewById(R.id.register_button);
        TextView hasAccount = (TextView) findViewById(R.id.txt_hasAccount);
        TextView professorRegistration = (TextView) findViewById(R.id.txt_profRegistration);
        nameTextInput = (TextInputLayout) findViewById(R.id.txt_nameInput);
        emailTextInput = (TextInputLayout) findViewById(R.id.txt_emailAddress);
        passwordTextInput = (TextInputLayout) findViewById(R.id.txt_password);
        nameInput = (EditText) findViewById(R.id.edit_txt_name);
        emailInput = (EditText) findViewById(R.id.edit_txt_emailAddress);
        passwordInput = (EditText) findViewById(R.id.edit_txt_password);

        // register button is clicked on the Student Registration page
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Erase prior error messages
                nameTextInput.setError("");
                nameTextInput.setErrorEnabled(false);

                emailTextInput.setError("");
                emailTextInput.setErrorEnabled(false);

                passwordTextInput.setError("");
                passwordTextInput.setErrorEnabled(false);

                // convert the text in the editText fields to strings
                name = nameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                // create the user only if all the textfields are not null
                if(!name.equals("") && !email.equals("") && !password.equals("")) {
                    createUser();
                    StudentData.setStudentmode(true); // Activate professor mode
                    startActivity(new Intent(StudentRegistration.this, StudentMain.class));
                }
                // make background red to indicate error to user.
                else {
                    if(name.equals("")) {
                        nameTextInput.setErrorEnabled(true);
                        nameTextInput.setError("You need to enter a name");
                    }
                    if(email.equals("")) {
                        emailTextInput.setErrorEnabled(true);
                        emailTextInput.setError("You need to enter an email");
                    }
                    if(password.equals("")) {
                        passwordTextInput.setErrorEnabled(true);
                        passwordTextInput.setError("You need to enter a password");
                    }
                }
            }
        });

        // has account is clicked, changes activity to login
        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentRegistration.this, Login.class));
                finish();
            }
        });

        professorRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentRegistration.this, ProfRegistration.class));
                finish();
            }
        });
    }

    public void createUser() {
        StudentData.clearAllData();
        StudentData.setName(name);
        StudentData.setEmail(email);
        StudentData.setPassword(password);
        Server.registerStudent(this, name, email, password, "s");
    }

    public void launchStudent(){
        finish();
        startActivity(new Intent(this, StudentMain.class));
    }
}
