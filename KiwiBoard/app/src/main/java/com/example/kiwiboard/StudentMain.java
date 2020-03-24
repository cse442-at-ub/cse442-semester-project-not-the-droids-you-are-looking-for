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

import java.util.Objects;

public class StudentMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        toolbar = (Toolbar) findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.student_drawer_layout);
        navigationView = findViewById(R.id.nav_student_view);
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
        if (currentcourse >= 0){
            coursetext = StudentData.getCourses().get(currentcourse).getCourseName();
        } else {
            coursetext = "";
        }

        setDrawerCourse(coursetext);

        TextView navsublbl = hview.findViewById(R.id.txtNavSublbl);
        navsublbl.setText(StudentData.getEmail());

        setToolbarText(coursetext);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                    new StudentHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_student_home);
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
                        new StudentLogFragment()).commit();
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

