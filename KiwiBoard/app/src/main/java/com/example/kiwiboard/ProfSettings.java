package com.example.kiwiboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfSettings extends AppCompatActivity {
    private static Context context;
    private Toolbar toolbar;
    private String email;
    private String confirmEmail;
    private String password;
    private EditText editTextEmail;
    private EditText editTextConfirmEmail;
    private EditText editTextPass;
    private TextInputLayout emailTextInput;
    private TextInputLayout confirmEmailTextInput;
    private TextInputLayout passwordTextInput;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private TextInputLayout currentPasswordTextInput;
    private TextInputLayout newPasswordTextInput;
    private TextInputLayout confirmPasswordTextInput;
    private List<String> courseList;
    private String[] courseNames;
    private List<String> studentList;
    private String[] studentNames;
    private ArrayList<Student>students;

    public static void LoadContext(Context mcontext){
        context = mcontext;
        Intent intent = new Intent(context,ProfSettings.class);
        context.startActivity(intent);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_settings);

        setToolbar("Settings");

        // Assign button ID to button
        Button button1 = findViewById(R.id.btnProfRemoveClass);
        Button button2 = findViewById(R.id.btnProfRemoveStudent);
        Button button3 = findViewById(R.id.btnProfChangeEmail);
        Button button4 = findViewById(R.id.btnProfChangePassword);

        //Click Listener
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                //Dialog Remove Classes
                AlertDialog.Builder sBuilder = new AlertDialog.Builder(ProfSettings.this);

                ArrayList<Course> courses = ProfData.getCourses();

                courseList = new ArrayList<String>();
                courseNames = new String[courses.size()];
                for(Course course: courses)
                {
                    courseList.add(course.getCourseName());
                }

                for(int i=0;i<courses.size();i++){
                    courseNames[i] = courseList.get(i);
                }

                //Fill checkedCourse with false boolean array
                final boolean[] checkedCourseArray=new boolean[courses.size()];
                for(int j=0;j<courses.size();j++)
                {
                    checkedCourseArray[j]=false;
                }

                sBuilder.setCancelable(true);
                sBuilder.setTitle("Remove Course");
                sBuilder.setMultiChoiceItems(courseNames, checkedCourseArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedCourseArray[which]=isChecked;
                        String currentItems = courseList.get(which);
                    }
                });

                sBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                sBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                if(courseList.isEmpty())
                {
                    sBuilder.setMessage("No Courses Left");
                }

                final AlertDialog dialog = sBuilder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Count how many true in boolean array
                        int Count=0;
                        for(int i=0;i<checkedCourseArray.length;i++)
                        {
                            if(checkedCourseArray[i]==true){
                                Count++;
                            }
                        }

                        //Allow user to continue else display with checked one or more
                        if(courseList.isEmpty()){
                            Toast.makeText(ProfSettings.this, "No Courses Left SMH", Toast.LENGTH_SHORT).show();
                        }
                        else if(Count>0) {
                            AlertDialog.Builder dBuilder = new AlertDialog.Builder(ProfSettings.this);
                            dBuilder.setTitle("Delete Course Confirmation");
                            dBuilder.setMessage("Are You Sure You Want To Delete The Selected Classes?");
                            dBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            final AlertDialog dialogx = dBuilder.create();
                            dialogx.show();
                            dialogx.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for(int i=0;i<checkedCourseArray.length;i++)
                                    {
                                        if(checkedCourseArray[i]==true){
                                            ProfData.removeCourse(i);
                                        }
                                    }
                                    dialogx.dismiss();
                                    Toast.makeText(ProfSettings.this, "Selected Courses Has Been Removed", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                        else{
                            Toast.makeText(ProfSettings.this, "Please Check One Of The Courses", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                //Dialog Remove Classes
                AlertDialog.Builder sBuilder = new AlertDialog.Builder(ProfSettings.this);

                // Declare variables
                int courseindex = ProfData.getCurrentcourse();

                // Check if there is any course
                if (courseindex < 0){
                    AlertDialog.Builder tBuilder = new AlertDialog.Builder(ProfSettings.this);
                    tBuilder.setTitle("Remove Student");
                    tBuilder.setMessage("No Students Left");
                    tBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    tBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    final AlertDialog dialog = tBuilder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ProfSettings.this, "No Students Left SMH", Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }

                // Get the current course
                final Course currentcourse = ProfData.getCourses().get(courseindex);
                students = currentcourse.getStudents();
                int numStudents = students.size();

                studentList = new ArrayList<String>();
                studentNames = new String[numStudents];
                for (Student student : students) {
                    studentList.add(student.getName());
                }

                for (int i = 0; i < students.size(); i++) {
                    studentNames[i] = studentList.get(i);
                }

                //Fill checkedCourse with false boolean array
                final boolean[] checkedCourseArray = new boolean[numStudents];
                for (int j = 0; j < numStudents; j++) {
                    checkedCourseArray[j] = false;
                }

                sBuilder.setCancelable(true);
                sBuilder.setTitle("Remove Student");
                sBuilder.setMultiChoiceItems(studentNames, checkedCourseArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedCourseArray[which] = isChecked;
                        String currentItems = studentList.get(which);
                    }
                });

                sBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                sBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                if (studentList.isEmpty()) {
                    sBuilder.setMessage("No Students Left");
                }

                final AlertDialog dialog = sBuilder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Count how many true in boolean array
                        int Count = 0;
                        for (int i = 0; i < checkedCourseArray.length; i++) {
                            if (checkedCourseArray[i] == true) {
                                Count++;
                            }
                        }

                        //Allow user to continue else display with checked one or more
                        if (studentList.isEmpty()) {
                            Toast.makeText(ProfSettings.this, "No Students Left SMH", Toast.LENGTH_SHORT).show();
                        } else if (Count > 0) {
                            AlertDialog.Builder dBuilder = new AlertDialog.Builder(ProfSettings.this);
                            dBuilder.setTitle("Delete Student Confirmation");
                            dBuilder.setMessage("Are You Sure You Want To Delete The Selected Students?");
                            dBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            final AlertDialog dialogx = dBuilder.create();
                            dialogx.show();
                            dialogx.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = checkedCourseArray.length-1; i >= 0; i--) {
                                        if (checkedCourseArray[i] == true) {
                                            currentcourse.removeStudent(i);
                                        }
                                    }
                                    dialogx.dismiss();
                                    Toast.makeText(ProfSettings.this, "Selected Students Has Been Removed", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(ProfSettings.this, "Please Check One Of The Students", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                View mView = getLayoutInflater().inflate(R.layout.activity_prof_change_email,null);

                //Send Email To TextView
                TextView currentE = mView.findViewById(R.id.txtProfDisplayCurrentEmail);
                currentE.setText(ProfData.getEmail());
                final String currentEs = currentE.getText().toString();
                StudentData.setEmail(currentEs);

                //Dialog For change Email
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfSettings.this);
                mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mBuilder.setTitle("Change Email");
                mBuilder.setView(mView);

                final AlertDialog dialogf = mBuilder.create();
                dialogf.show();
                dialogf.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTextEmail = (EditText) dialogf.findViewById(R.id.editTxt_Prof_emailAddress);
                        editTextConfirmEmail = (EditText) dialogf.findViewById(R.id.editTxt_Prof_confirmEmailAddress);
                        editTextPass = (EditText) dialogf.findViewById(R.id.editTxt_Prof_confirmEmailPass);
                        emailTextInput = (TextInputLayout) dialogf.findViewById(R.id.txt_Prof_newEmail);
                        confirmEmailTextInput = (TextInputLayout) dialogf.findViewById(R.id.txt_Prof_confirmEmail);
                        passwordTextInput = (TextInputLayout) dialogf.findViewById(R.id.txt_Prof_pass);

                        email = editTextEmail.getText().toString();
                        confirmEmail = editTextConfirmEmail.getText().toString();
                        password = editTextPass.getText().toString();

                        // Erase prior error messages
                        emailTextInput.setError("");
                        emailTextInput.setErrorEnabled(false);

                        confirmEmailTextInput.setError("");
                        confirmEmailTextInput.setErrorEnabled(false);

                        passwordTextInput.setError("");
                        passwordTextInput.setErrorEnabled(false);

                        // create the user only if all the textfields are not null
                        if(!email.equals("") && !confirmEmail.equals("") && !password.equals("") && isEmailValid(email)==true && isEmailValid(confirmEmail)==true && !currentEs.equals(email)
                                && !currentEs.equals(confirmEmail) && email.equals(confirmEmail) && confirmEmail.equals(email)){
                            ProfData.setEmail(email);
                            Toast.makeText(ProfSettings.this, "Email Address Has Been Changed", Toast.LENGTH_SHORT).show();
                            ProfMain profMain = (ProfMain) context;
                            profMain.setEmailText(email);
                            dialogf.dismiss();
                        }
                        // make background red to indicate error to user.
                        else {
                            if(email.equals("")) {
                                emailTextInput.setErrorEnabled(true);
                                emailTextInput.setError("Please Fill In A Email");
                            }
                            if(confirmEmail.equals("")) {
                                confirmEmailTextInput.setErrorEnabled(true);
                                confirmEmailTextInput.setError("Please Fill In The Confirm Email");
                            }
                            if(password.equals("")) {
                                passwordTextInput.setErrorEnabled(true);
                                passwordTextInput.setError("Please Fill In Your Password");
                            }
                            if(isEmailValid(email)==false && !email.equals("")){
                                emailTextInput.setErrorEnabled(true);
                                emailTextInput.setError("Please Put A Real Email");
                            }
                            if(isEmailValid(confirmEmail)==false && !confirmEmail.equals("")){
                                confirmEmailTextInput.setErrorEnabled(true);
                                confirmEmailTextInput.setError("Please Put A Real Confirm Email");
                            }
                            if(currentEs.equals(email)){
                                emailTextInput.setErrorEnabled(true);
                                emailTextInput.setError("New Email Matches with Old Email");
                            }
                            if(currentEs.equals(confirmEmail)){
                                confirmEmailTextInput.setErrorEnabled(true);
                                confirmEmailTextInput.setError("Confirm Email Matches with Old Email");
                            }
                            if(!email.equals(confirmEmail)){
                                emailTextInput.setErrorEnabled(true);
                                emailTextInput.setError("New Email Doesn't Match");
                            }
                            if(!confirmEmail.equals(email)){
                                confirmEmailTextInput.setErrorEnabled(true);
                                confirmEmailTextInput.setError("Confirm Email Doesn't Match");
                            }
                        }
                    }
                });
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                AlertDialog.Builder nBuilder = new AlertDialog.Builder(ProfSettings.this);
                View nView = getLayoutInflater().inflate(R.layout.activity_student_change_password, null);
                nBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                nBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                nBuilder.setTitle("Change Password");
                nBuilder.setView(nView);

                final AlertDialog dial = nBuilder.create();
                dial.show();
                dial.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTextCurrentPassword = dial.findViewById(R.id.editTxt_Prof_currentPass);
                        editTextNewPassword = dial.findViewById(R.id.editTxt_Prof_newPass);
                        editTextConfirmPassword = dial.findViewById(R.id.editTxt_Prof_confirmPass);
                        currentPasswordTextInput = dial.findViewById(R.id.txt_Prof_currentPass);
                        newPasswordTextInput = dial.findViewById(R.id.txt_Prof_newPass);
                        confirmPasswordTextInput = dial.findViewById(R.id.txt_Prof_confirmPass);

                        currentPassword = editTextCurrentPassword.getText().toString();
                        newPassword = editTextNewPassword.getText().toString();
                        confirmPassword = editTextConfirmPassword.getText().toString();

                        // Erase prior error messages
                        currentPasswordTextInput.setError("");
                        currentPasswordTextInput.setErrorEnabled(false);

                        newPasswordTextInput.setError("");
                        newPasswordTextInput.setErrorEnabled(false);

                        confirmPasswordTextInput.setError("");
                        confirmPasswordTextInput.setErrorEnabled(false);

                        // create the user only if all the textfields are not null
                        if (!currentPassword.equals("") && !newPassword.equals("") && !confirmPassword.equals("") && newPassword.equals(confirmPassword)
                                && confirmPassword.equals(newPassword) &&  !newPassword.equals(currentPassword) && !confirmPassword.equals(currentPassword)) {
                            Toast.makeText(ProfSettings.this, "Password Has Been Changed", Toast.LENGTH_SHORT).show();
                            dial.dismiss();
                        }
                        // make background red to indicate error to user.
                        else {
                            if (currentPassword.equals("")) {
                                currentPasswordTextInput.setErrorEnabled(true);
                                currentPasswordTextInput.setError("Please Fill In Your Current Password");
                            }
                            if (newPassword.equals("")) {
                                newPasswordTextInput.setErrorEnabled(true);
                                newPasswordTextInput.setError("Please Fill In Your New Password");
                            }
                            if (confirmPassword.equals("")) {
                                confirmPasswordTextInput.setErrorEnabled(true);
                                confirmPasswordTextInput.setError("Please Fill In Your Confirm Password");
                            }
                            if (!newPassword.equals(confirmPassword)) {
                                newPasswordTextInput.setErrorEnabled(true);
                                newPasswordTextInput.setError("New Password Doesn't Match Confirm Password");
                            }
                            if (!confirmPassword.equals(newPassword)) {
                                confirmPasswordTextInput.setErrorEnabled(true);
                                confirmPasswordTextInput.setError("Confirm Password Doesn't Match New Password");
                            }
                            if (newPassword.equals(currentPassword)) {
                                newPasswordTextInput.setErrorEnabled(true);
                                newPasswordTextInput.setError("New Password Matches Current Password");
                            }
                            if (confirmPassword.equals(currentPassword)) {
                                confirmPasswordTextInput.setErrorEnabled(true);
                                confirmPasswordTextInput.setError("Confirm Password Matches Current Password");
                            }
                        }
                    }
                });
            }
        });
    }

    public void setToolbar(String title){
        toolbar = (Toolbar) findViewById(R.id.settings_prof_toolbar);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        params.topMargin = getStatusBarHeight();
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
