package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class IndividualGrades extends AppCompatActivity {
    private ArrayList<Question> questions;      // Question list
    private Toolbar toolbar;
    private String studentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_grades);
        setToolbar("Grades");

        // Declare variables
        int courseindex = ProfData.getCurrentcourse();
        int Position = ProfData.getLastclickedstudent();
        Student student;
        ArrayList<Integer> list = new ArrayList<>();
        ProfData data;

        // Get the current course
        Course currentcourse = ProfData.getCourses().get(courseindex);
        student =currentcourse.getStudents().get(Position);

        ArrayList<Question> questions = currentcourse.getQuestions();
        Question question;
        double pointsreceived;
        double percentage;
        int maxpoints;
        ArrayList<Double> averages = currentcourse.calculateAverages();

        // Display student name to text view
        studentName = student.getName();
        TextView StuName = (TextView) findViewById(R.id.txtStuname);
        StuName.setText(" "+studentName);

        // Table layout
        TableLayout gradesTable;
        gradesTable =(TableLayout) findViewById(R.id.tblInvidualgrades);

        for(int i = 0; i<questions.size();i++) {
            TableRow tr = new TableRow(this);

            tr.setId(i);
            //tr.setBackgroundResource(R.color.colorPrimaryDark);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            question = student.getQuestions().get(i);
            pointsreceived = question.calculateScore();
            maxpoints = question.getMaxpoints();
            percentage = question.calculatePercentage();

            //TEXTVIEWS********
            TextView qnum = new TextView(this);
            qnum.setText("" + question.getQuestionnumber());
            qnum.setId(i);
            //qnum.setTextColor(Color.WHITE);
            qnum.setTextSize(20);
            qnum.setPadding(30, 5, 80, 5);
            tr.addView(qnum);

            TextView numpoints = new TextView(this);
            numpoints.setText("" + pointsreceived);
            numpoints.setId(i + i);
            //numpoints.setTextColor(Color.WHITE);
            numpoints.setTextSize(20);
            numpoints.setPadding(10, 5, 80, 0);
            tr.addView(numpoints);

            TextView totalpoints = new TextView(this);
            totalpoints.setText("" + maxpoints);
            totalpoints.setId(i + i);
            //totalpoints.setTextColor(Color.WHITE);
            totalpoints.setTextSize(20);
            totalpoints.setPadding(10, 5, 80, 0);
            tr.addView(totalpoints);

            TextView percent = new TextView(this);
            percent.setText("" + percentage);
            percent.setId(i + i);
            //percent.setTextColor(Color.WHITE);
            percent.setTextSize(20);
            percent.setPadding(10, 5, 80, 0);
            tr.addView(percent);


            ProgressBar tv3 = new ProgressBar(new ContextThemeWrapper(this, R.style.horizontalProgressSmall), null, 0);
            tv3.setProgress((int) percentage);
            tv3.setId(i + i + i);
            tv3.setMinimumWidth(25);
            tv3.setPadding(25, 20, 5, 10);
            tr.addView(tv3);

            gradesTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void setToolbar(String title){
        toolbar = (Toolbar) findViewById(R.id.individual_grades_toolbar);
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



}
