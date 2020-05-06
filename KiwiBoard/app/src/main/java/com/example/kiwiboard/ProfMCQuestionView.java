package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfMCQuestionView extends AppCompatActivity {
    private ArrayList<Question> questions;
    private Course currentCourse;
    private int courseIndex;
    private int questionIndex;
    private Question question;
    private TextView txt_questionNumber;
    private TextView txt_questionDescription;
    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private ArrayList<String> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_mc_question_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profMC_toolbar) ;
        setToolbar("Question Log", toolbar);

        txt_questionNumber = findViewById(R.id.profMC_questionNumber);
        txt_questionDescription = findViewById(R.id.profMC_questionTextView);
        radioGroup = findViewById(R.id.profMC_choices);
        rb1 = findViewById(R.id.profMC_multchoice1);
        rb2 = findViewById(R.id.profMC_multchoice2);
        rb3 = findViewById(R.id.profMC_multchoice3);
        rb4 = findViewById(R.id.profMC_multchoice4);

        courseIndex = ProfData.getCurrentcourse();
        currentCourse = ProfData.getCourses().get(courseIndex);
        questions = currentCourse.getQuestions();
        choices = questions.get(courseIndex).getChoices();
        displayQuestion();

        if(question.getSubmissionEntered()) {
            int submission_id = question.getMcresponse();
            if(submission_id != question.getMcanswer()) {
                if(submission_id == 1) {
                    rb1.setTextColor(Color.RED);
                }
                else if(submission_id == 2) {
                    rb2.setTextColor(Color.RED);
                }
                else if(submission_id == 3) {
                    rb3.setTextColor(Color.RED);
                }
                else if(submission_id == 4) {
                    rb4.setTextColor(Color.RED);
                }
            }
        }
    }


    private Question getQuestion(){
        int courseindex = ProfData.getCurrentcourse();
        if(courseindex < 0) {
            return null;
        }
        Course currentcourse = ProfData.getCourses().get(courseindex);

        int qindex = ProfData.getLastclickedquestion();
        if (qindex < 0)
            return null;
        ArrayList<Question> questions = currentcourse.getQuestions();
        return questions.get(qindex);
    }

    private void displayQuestion() {
        radioGroup.clearCheck();

        question = getQuestion();
        if(question == null) {
            return;
        }

        txt_questionDescription.setText(question.getDescription());     // set text for question description
        txt_questionNumber.setText("#" + question.getQuestionnumber());     // set text for question number

        if(question.getChoices() != null) {
            // populate the choices into the radio button texts
            choices = questions.get(question.getQuestionnumber() - 1).getChoices();
            int answer_index = question.getMcanswer();

            RadioButton rblist[] = new RadioButton[4];
            rblist[0] = findViewById(R.id.profMC_multchoice1);
            rblist[1] = findViewById(R.id.profMC_multchoice2);
            rblist[2] = findViewById(R.id.profMC_multchoice3);
            rblist[3] = findViewById(R.id.profMC_multchoice4);

            int i = 0;
            // populate the choices into the radio button texts
            boolean same = false;
            for (i = 0; i < 4; i++){

                if(i == question.getMcresponse() && i == answer_index){
                    same = true;
                    rblist[i].setTextColor(Color.GREEN);
                    rblist[i].setChecked(true);
                }
                if(i == question.getMcresponse() && !same) {
                    rblist[i].setTextColor(Color.RED);
                    rblist[i].setChecked(true);
                }
                if(i == answer_index && !same){
                    rblist[i].setTextColor(Color.GREEN);
                }
                if(i < choices.size())
                    rblist[i].setText(choices.get(i));
                else
                    rblist[i].setVisibility(View.GONE);
            }

//            rb1.setText(choice1);       // rb1 is choices [0]
//            rb2.setText(choice2);       // rb2 is choices [1]
//            rb3.setText(choice3);       // rb3 is choices [2]
//            rb4.setText(choice4);       // rb4 is choices [3]
//
//            // display the correct answer to the prof
//            if(answer_index == 0)
//                rb1.setTextColor(Color.GREEN);
//            else if(answer_index == 1)
//                rb2.setTextColor(Color.GREEN);
//            else if(answer_index == 2)
//                rb3.setTextColor(Color.GREEN);
//            else if(answer_index == 3)
//                rb4.setTextColor(Color.GREEN);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
    }


    public void setToolbar(String title, Toolbar toolbar) {
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
