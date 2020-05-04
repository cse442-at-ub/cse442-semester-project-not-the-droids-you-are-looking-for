package com.example.kiwiboard;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class ProfRegistration extends AppCompatActivity {
    private String name;
    private String email;
    private String password;
    private String university;
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText universityInput;
    private TextInputLayout nameTextInput;
    private TextInputLayout emailTextInput;
    private TextInputLayout passwordTextInput;
    private TextInputLayout universityTextInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_registration);

        Button registerButton = (Button) findViewById(R.id.prof_register_button);
        TextView hasAccount = (TextView) findViewById(R.id.prof_txt_hasAccount);
        TextView studentReg = (TextView) findViewById(R.id.txt_studentReg);
        nameTextInput = (TextInputLayout) findViewById(R.id.prof_txt_nameInput);
        emailTextInput = (TextInputLayout) findViewById(R.id.prof_txt_emailAddress);
        passwordTextInput = (TextInputLayout) findViewById(R.id.prof_txt_password);
        universityTextInput = (TextInputLayout) findViewById(R.id.prof_txt_university);
        nameInput = (EditText) findViewById(R.id.editText_name_prof_reg);
        emailInput = (EditText) findViewById(R.id.editText_email_address_prof_reg);
        passwordInput = (EditText) findViewById(R.id.editText_password_prof_reg);
        universityInput = (EditText) findViewById(R.id.editText_university_prof_reg);

        // register button is clicked on the Professor Registration page
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

                universityTextInput.setError("");
                universityTextInput.setErrorEnabled(false);

                // convert the text in the editText fields to strings
                name = nameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                university = universityInput.getText().toString();

                // create the user only if all the textfields are not null
                if(!name.equals("") && !email.equals("") && !password.equals("") && !university.equals("")) {
                    createUser();
                    ProfData.setProfessormode(true); // Activate professor mode
                    startActivity(new Intent(ProfRegistration.this, ProfMain.class));
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
                    if(university.equals("")) {
                        universityTextInput.setErrorEnabled(true);
                        universityTextInput.setError("You need to enter a university");
                    }
                }
            }
        });

        // has account is clicked, changes activity to login
        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfRegistration.this, Login.class));
                finish();
            }
        });

        // student Registration text is clicked, switch activities to student Registration
        studentReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfRegistration.this, StudentRegistration.class));
                finish();
            }
        });
    }

    public void createUser() {
        ProfData.clearAllData();
        ProfData.setName(name);
        ProfData.setEmail(email);
        ProfData.setPassword(password);
        ProfData.setUniversity(university);
        Server.registerProfessor(this, name, email, password, "p");
    }

    public void launchProfessor(){
        finish();
        startActivity(new Intent(this, ProfMain.class));
    }
}
