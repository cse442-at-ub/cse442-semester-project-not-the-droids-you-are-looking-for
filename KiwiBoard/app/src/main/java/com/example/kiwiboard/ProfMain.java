package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ProfMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main);

        Toolbar toolbar = findViewById(R.id.professor_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.professor_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_professor_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                    new ProfessorHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_professor_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_professor_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.professor_fragment_container,
                        new ProfessorHomeFragment()).commit();
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
                        new ProfessorLogFragment()).commit();
                break;
            case R.id.nav_professor_logout:
                // Log the user out here
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                this.finish();
                startActivity(new Intent(ProfMain.this, Mode.class));
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
}


