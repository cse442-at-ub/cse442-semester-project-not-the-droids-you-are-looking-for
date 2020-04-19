package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ProfMultipleChoice extends AppCompatActivity {
    private long COUNTDOWN_IN_MILLIS = 60000;
    private ArrayList<Question> questions;
    private int answer_index;
    ArrayList<String> choices;
    private Course currentCourse;
    private int courseIndex;
    private int questionIndex;
    private ProgressBar progressBar;
    private TextView txt_timerText;
    private TextView txt_questionNumber;
    private TextView txt_questionDescription;
    private Button displayAnswer;
    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long backPressedTime;

    //private ColorStateList textColorDefaultRb;      // to store original text color of radiobutton
    //private ColorStateList textColor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_multiple_choice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.prof_MC_toolbar);
        setToolbar("Multiple Choice", toolbar);

        txt_questionNumber = findViewById(R.id.profQuestionNumber);
        txt_timerText = findViewById(R.id.prof_txt_countdown);
        progressBar = findViewById(R.id.profProgressBar);
        radioGroup = findViewById(R.id.profmultipleChoiceOptions);
        rb1 = findViewById(R.id.profmultchoice1);
        rb2 = findViewById(R.id.profmultchoice2);
        rb3 = findViewById(R.id.profmultchoice3);
        rb4 = findViewById(R.id.profmultchoice4);
        displayAnswer = findViewById(R.id.showAnswerButton);

        //textColorDefaultRb = rb1.getTextColors();
        courseIndex = ProfData.getCurrentcourse();
        currentCourse = ProfData.getCourses().get(courseIndex);
        questions = currentCourse.getQuestions();
        choices = questions.get(courseIndex).getChoices();
        progressBar.setVisibility(View.VISIBLE);
        displayAnswer.setVisibility(View.INVISIBLE);    // set the button to invisible until timer runs out

        displayQuestion();
    }

    private void displayQuestion() {
        radioGroup.clearCheck();

        Question question = getQuestion();
        answer_index = question.getMcanswer();
        //question.get

        if(question == null)
            return;

        COUNTDOWN_IN_MILLIS = question.getTimelimit() * 1000;   // get the time limit stored in questions data
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;                 // set the timeLeft in millis

        txt_questionDescription = findViewById(R.id.questionTextView);
        txt_questionDescription.setText(question.getDescription());     // set text for question description
        txt_questionNumber.setText("#" + question.getQuestionnumber());     // set text for question number

        if(question.getChoices() != null) {
            // populate the choices into the radio button texts
            choices = questions.get(question.getQuestionnumber() - 1).getChoices();
            String choice1 = choices.get(0);
            String choice2 = choices.get(1);
            String choice3 = choices.get(2);
            String choice4 = choices.get(3);

            rb1.setText(choice1);       // rb1 is choices [0]
            rb2.setText(choice2);       // rb2 is choices [1]
            rb3.setText(choice3);       // rb3 is choices [2]
            rb4.setText(choice4);       // rb4 is choices [3]
        }
        startTimer(question);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private Question getQuestion() {
        int courseindex = ProfData.getCurrentcourse();
        if(courseindex < 0) {
            return null;
        }
        Course currentcourse = ProfData.getCourses().get(courseindex);
        this.questionIndex = ProfData.getLastclickedquestion();
        if (this.questionIndex < 0)
            return null;
        ArrayList<Question> questions = currentcourse.getQuestions();
        return questions.get(this.questionIndex);
    }

    private void startTimer(final Question question) {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                // need to set the percentage for the progress bar
                // 1. Progress Bar only had 100 different settings. 0-100 integers
                int progress = (int) (100 * timeLeftInMillis / COUNTDOWN_IN_MILLIS);
                progressBar.setProgress(progress);

            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                countDownTimer.cancel();
                progressBar.clearAnimation();
                progressBar.setVisibility(View.INVISIBLE);
                displayAnswer.setVisibility(View.VISIBLE);
                displayAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Change the correct answer's text to Green
                        // the index of the correct answer was stored in displayQuestion
                        if(answer_index == 0)
                            rb1.setTextColor(Color.GREEN);
                        else if(answer_index == 1)
                            rb2.setTextColor(Color.GREEN);
                        else if(answer_index == 2)
                            rb3.setTextColor(Color.GREEN);
                        else if(answer_index == 3)
                            rb4.setTextColor(Color.GREEN);
                    }
                });
                // checkAnswer will lock in the answer selected when time runs out
                // when coded cancel the countDownTimer inside of it
                // checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // only get remaining after dividing by 60
        // "%02d:%02d" : time string that looks like a clock
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        setTimerText(timeLeftFormatted);

        if(timeLeftInMillis < 10000) {
            txt_timerText.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
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
    public void setTimerText(String time) {
        txt_timerText.setText(time);
    }

}
