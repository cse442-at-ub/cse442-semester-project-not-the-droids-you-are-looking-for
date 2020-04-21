package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class StudentMCQuestionView extends AppCompatActivity {
    private ArrayList<Question> questions;
    private Course currentCourse;
    private int courseIndex;
    private int questionIndex;
    private TextView txt_questionNumber;
    private TextView txt_questionDescription;
    private RadioGroup radioGroup;
    private ArrayList<String> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_m_c_question_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.studentMC_toolbar) ;
        setToolbar("Question Log", toolbar);

        txt_questionNumber = findViewById(R.id.studentMC_questionNumber);
        txt_questionDescription = findViewById(R.id.studentMC_questionTextView);
        radioGroup = findViewById(R.id.studentMC_choices);


        courseIndex = StudentData.getCurrentcourse();
        currentCourse = StudentData.getCourses().get(courseIndex);
        questions = currentCourse.getQuestions();
        choices = questions.get(courseIndex).getChoices();
        displayQuestion();
    }


    private Question getQuestion(){
        int courseindex = StudentData.getCurrentcourse();
        if(courseindex < 0) {
            return null;
        }
        Course currentcourse = StudentData.getCourses().get(courseindex);

        int qindex = StudentData.getLastclickedquestion();
        if (qindex < 0)
            return null;
        ArrayList<Question> questions = currentcourse.getQuestions();
        return questions.get(qindex);
    }

    private void displayQuestion() {
        radioGroup.clearCheck();

        Question question = getQuestion();
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
            rblist[0] = findViewById(R.id.studentMC_multchoice1);
            rblist[1] = findViewById(R.id.studentMC_multchoice2);
            rblist[2] = findViewById(R.id.studentMC_multchoice3);
            rblist[3] = findViewById(R.id.studentMC_multchoice4);

            int i = 0;
            // populate the choices into the radio button texts
            for (i = 0; i < 4; i++){

                if(i == answer_index) {
                    rblist[i].setTextColor(Color.GREEN);
                    rblist[i].setChecked(true);
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
//            // display the correct answer to the student
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
