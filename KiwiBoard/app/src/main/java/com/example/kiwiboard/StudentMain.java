package com.example.kiwiboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        loadDummyData();
        Toolbar toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.student_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_student_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Change nav bar content
        View hview = navigationView.getHeaderView(0);
        ImageView navImage = hview.findViewById(R.id.navImage);
        navImage.setImageResource(R.drawable.hat);
        navImage.getLayoutParams().height = 200;
        navImage.getLayoutParams().width = 200;

        String coursetext;
        int currentcourse = StudentData.getCurrentcourse();
        if (currentcourse != -1){
            coursetext = StudentData.getCourses().get(currentcourse).getCourseName();
        } else {
            coursetext = "No course selected";
        }
        TextView navheaderlbl = hview.findViewById(R.id.txtnavHeaderlbl);
        navheaderlbl.setText(coursetext);

        TextView navsublbl = hview.findViewById(R.id.txtNavSublbl);
        navsublbl.setText(StudentData.getEmail());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                    new StudentHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_student_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_student_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                        new StudentHomeFragment()).commit();
                break;
            case R.id.nav_student_grades:
                getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                        new StudentGradesFragment()).commit();
                break;
            case R.id.nav_student_classes:
                getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                        new StudentCoursesFragment()).commit();
                break;
            case R.id.nav_student_addclass:
                getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                        new AddCourseFragment()).commit();
                break;
            case R.id.nav_student_question_log:
                getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                        new QuestionLogFragment()).commit();
                break;
            case R.id.nav_student_mc:
                startActivity(new Intent(StudentMain.this, MultipleChoice.class));
                break;
            case R.id.nav_student_sa:
                startActivity(new Intent(StudentMain.this, ShortAnswer.class));
                break;
            case R.id.nav_student_logout:
                // Log the user out here
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                this.finish();
                startActivity(new Intent(StudentMain.this, Mode.class));
                break;
            case R.id.nav_student_settings:
                startActivity(new Intent(StudentMain.this, StuSettings.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadDummyData() {
        // Loading dummy data into Questions
        ArrayList<String> choices;
        String description;
        int mcanswer;
        Question q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15;
        description = "Which wave causes the most damage to buildings?";
        choices = new ArrayList<>(Arrays.asList("love waves", "red waves", "primary waves", "secondary waves"));
        mcanswer = 0;
        q1 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, 0,0,null, null, 0, null, null, 0);

        choices = new ArrayList<>(Arrays.asList("the biosphere", "the geosphere", "the hydrosphere", "the atmosphere"));
        description = "What subsystem or sphere represents the solid rock of the Earth?";
        mcanswer = 1;
        q2 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "A __________ studies the origin of Earth's landscapes.";
        choices = new ArrayList<>(Arrays.asList("hydrologist", "paleontologist", "structural geologist", "geomorphologist"));
        mcanswer = 3;
        q3 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "The __________ includes organisms living on Earth and their environments.";
        choices = new ArrayList<>(Arrays.asList("the atmosphere", "the geosphere", "the hydrosphere", "the biosphere"));
        mcanswer = 3;
        q4 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What part of the Earth is thought to be composed of iron and nickel?";
        choices = new ArrayList<>(Arrays.asList("the mantle", "the crust", "the core", "the lithosphere"));
        mcanswer = 2;
        q5 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        choices = new ArrayList<>(Arrays.asList("about 100 million years old", "4.567 billion years old", "about 6,000 years old", "as old as the hills and twice as dusty"));
        description = "Geologists generally agree that the Earth is __________";
        mcanswer = 1;
        q6 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Earth's external heat engine is driven by what source of energy?";
        choices = new ArrayList<>(Arrays.asList("solar power", "coal", "natural gas", "petroleum"));
        mcanswer = 0;
        q7 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "The most voluminous portion of the Earth is known to geologists as _____.";
        choices = new ArrayList<>(Arrays.asList("the lithosphere", "the mantle", "the core", "the crust"));
        mcanswer = 1;
        q8 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which of the following Earth processes and landscapes are related to slow rates of change?";
        choices = new ArrayList<>(Arrays.asList("volcano erupting down a mountain side", "erosion of a canyon", "earthquake in California"));
        mcanswer = 1;
        q9 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which type of mining is more efficient and less expensive?";
        choices = new ArrayList<>(Arrays.asList("surface mining", "shaft mining", "borehole mining", "underground mining"));
        mcanswer = 0;
        q10 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What is the most fundamental resource derived from our planet Earth?";
        choices = new ArrayList<>(Arrays.asList("steel", "gold", "water", "titanium"));
        mcanswer = 2;
        q11 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which internal processes are responsible for plate movements of the Earth?";
        choices = new ArrayList<>(Arrays.asList("convection currents", "subduction currents", "conduction currents", "heat currents"));
        mcanswer = 0;
        q12 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which step comes first in a scientific method?";
        choices = new ArrayList<>(Arrays.asList("gather evidence", "test hypothesis", "develop theory", "observation"));
        mcanswer = 3;
        q13 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "In 1912, a German meteorologist named _____________ began lecturing and writing scientific papers about continental drift?";
        choices = new ArrayList<>(Arrays.asList("James Hutton", "Alfred Wegener", "Charles Lyell", "Harry Hess"));
        mcanswer = 1;
        q14 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What fossil plant evidence supports the continental drift theory?";
        choices = new ArrayList<>(Arrays.asList("Glassopteris", "Lycophyta", "Ginkgo", "Equisetum"));
        mcanswer = 0;
        q15 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
        questions.add(q11);
        questions.add(q12);
        questions.add(q13);
        questions.add(q14);
        questions.add(q15);
        for (int i = 0; i < questions.size(); i++){
            q1 = questions.get(i);
            q1.setQuestionnumber(i+1);
            questions.set(i, q1);
        }

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("John", "jfebedai12@gmail.com", questions));
        students.add(new Student("Mary", "mhill@gmail.com", questions));
        students.add(new Student("Wendy", "wtulip23@gmail.com", questions));
        students.add(new Student("Edward", "edwhoff3@gmail.com", questions));
        students.add(new Student("Kyle", "kmitchel93@gmail.com", questions));
        students.add(new Student("Emily", "ehanson853@gmail.com", questions));
        students.add(new Student("Andrew", "adijkstra213@gmail.com", questions));
        students.add(new Student("Molly", "meverson425@gmail.com", questions));
        students.add(new Student("Justin", "jmanning554@gmail.com", questions));
        students.add(new Student("Becky", "btellys239@gmail.com", questions));

        Course Geology = new Course("Geology", "Kimberly Meehan", 10323, questions, students);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Geology);

        StudentData.setCourses(courses);
        StudentData.setCurrentcourse(courses.indexOf(Geology));
        StudentData.setName("John");
        StudentData.setEmail("jfebedai12@gmail.com");
        StudentData.setPassword("iamastudent1");

        ProfData.setCourses(courses);
        ProfData.setCurrentcourse(courses.indexOf(Geology));
        ProfData.setName("Kimberly Meehan");
        ProfData.setEmail("kmeehan234@gmail.com");
        ProfData.setPassword("iamaprofessor2");
    }

    public void studentMode(View view){
        this.finish();
        startActivity(new Intent(StudentMain.this, StudentMain.class));
    }

    public void professorMode(View view){
        this.finish();
        startActivity(new Intent(StudentMain.this, ProfMain.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}



