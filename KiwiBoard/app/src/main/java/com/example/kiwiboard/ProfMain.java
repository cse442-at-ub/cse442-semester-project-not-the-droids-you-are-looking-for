package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import java.util.Objects;


public class ProfMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener, LauncherDialog.LauncherDialogListener {
    
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private LauncherDialog launcherDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_main);

        Toolbar toolbar = findViewById(R.id.professor_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.professor_drawer_layout);
        navigationView = findViewById(R.id.nav_professor_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Change nav bar content
        View hview = navigationView.getHeaderView(0);

        // Set the navigation drawer image
        ImageView navImage = hview.findViewById(R.id.navImage);
        navImage.setImageResource(R.drawable.professor);
        navImage.getLayoutParams().height = 200;
        navImage.getLayoutParams().width = 200;

        if (ProfData.getCourses() != null && ProfData.getCourses().size() > 0)
            ProfData.setCurrentcourse(0);

        String coursetext;
        int currentcourse = ProfData.getCurrentcourse();
        if (currentcourse != -1){
            coursetext = ProfData.getCourses().get(currentcourse).getCourseName();
        } else {
            coursetext = "No course selected";
        }
        TextView navheaderlbl = hview.findViewById(R.id.txtnavHeaderlbl);
        navheaderlbl.setText(coursetext);

        TextView navsublbl = hview.findViewById(R.id.txtNavSublbl);
        navsublbl.setText(ProfData.getEmail());

        if (savedInstanceState == null) {
            if (ProfData.getCourses() == null || ProfData.getCourses().size() == 0){
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new CreateCourseFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_professor_create_class);
           } else{
                //getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
               //         new ProfHomeFragment()).commit();
               // navigationView.setCheckedItem(R.id.nav_professor_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfCoursesFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_professor_classes);
            }
        }
    }

    public void setToolbarText(String text){
        Objects.requireNonNull(getSupportActionBar()).setTitle(text);
    }

    public void setDrawerCourse(String text){
        View hview = navigationView.getHeaderView(0);
        TextView navheaderlbl = hview.findViewById(R.id.txtnavHeaderlbl);
        navheaderlbl.setText(text);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_professor_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfHomeFragment()).commit();
                break;
            case R.id.nav_professor_course_resources:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfCourseResourcesFragment()).commit();
                break;
            case R.id.nav_professor_grades:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfReportFragment()).commit();
                break;
            case R.id.nav_professor_classes:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfCoursesFragment()).commit();
                break;
            case R.id.nav_professor_create_class:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new CreateCourseFragment()).commit();
                break;
            case R.id.nav_professor_question_log:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfQuestionLogFragment()).commit();
                break;
            case R.id.nav_professor_roster:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfRosterFragment()).commit();
                break;
            case R.id.nav_professor_logout:
                // Log the user out here
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                this.finish();

                startActivity(new Intent(ProfMain.this, Login.class));
                break;
            case R.id.nav_professor_settings:
                startActivity(new Intent(ProfMain.this, ProfSettings.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Buttons for fragments

    public void switchToHome(){
        getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                new ProfHomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_professor_home);
    }

    public void createQuestionMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.question_creation_menu);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.mcoption:
                startActivity(new Intent(this, CreateMultipleChoice.class));
                break;
            case R.id.saoption:
                startActivity(new Intent(this, CreateShortAnswer.class));
                break;
            default:
                return false;
        }
        return true;
    }

    // Queue functionlity
    public void openLauncherDialog(){
        launcherDialog = new LauncherDialog();
        launcherDialog.show(getSupportFragmentManager(), "launcher dialog");
    }
    public void closeLanucherDialog(){
        launcherDialog.dismiss();
    }

    // Refreshes the homepage for when a question is added to the queue or launched
    public void refreshHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                new ProfHomeFragment()).commit();
    }

    // Gets the time info from the launcher dialog and completes the question launch process
    @Override
    public void launchQuestion(int minutes, int seconds) {
        int cindex = ProfData.getCurrentcourse();               // Get the current course index
        Course course = ProfData.getCourse(cindex);             // Get the current course
        int qindex = ProfData.getLastclickedquestion();         // Get the last clicked question index
        Question question = course.getQueueQuestion(qindex);    // Get the last clicked question

        course.removeQueueQuestion(qindex);                      // Remove the question from the queue
        question.setTimelimit(60*minutes + seconds);             // Set the question time limit in seconds
        question.setInQueue(false);                              // The question is no longer in the queue
        question.setActive(true);                                // The question is now active
        question.setTimelaunched(System.currentTimeMillis());    // time that the question was launched
        course.addQuestion(question);                            // Add the question into the question list
        ProfData.setCourse(cindex, course);                      // Update ProfData with the new course info
        refreshHome();
    }

    public void editQueueQuestion(){
        int cindex = ProfData.getCurrentcourse();               // Get the current course index
        Course course = ProfData.getCourse(cindex);             // Get the current course
        int qindex = ProfData.getLastclickedquestion();         // Get the last clicked question index
        Question question = course.getQueueQuestion(qindex);    // Get the last clicked question
        if(question.getType() == Question.QuestionType.MULTIPLECHOICE || question.getType() == Question.QuestionType.TRUEFALSE) {
            startActivity(new Intent(this, EditMultipleChoice.class));
        }
        if(question.getType() == Question.QuestionType.SHORTANSWER || question.getType() == Question.QuestionType.FILLINBLANK) {
            startActivity(new Intent(this, EditShortAnswer.class));
        }
    }

    public void deleteQueueQuestion(){
        int cindex = ProfData.getCurrentcourse();          // Get the current course index
        Course course = ProfData.getCourse(cindex);        // Get the current course
        int qindex = ProfData.getLastclickedquestion();    // Get the last clicked question index
        course.removeQueueQuestion(qindex);   // Remove the question from the queue
        ProfData.setCourse(cindex, course);   // Update ProfData with the new course info
        refreshHome();
    }

}


