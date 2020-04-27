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

import java.sql.Timestamp;
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
    private Question question;
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
    private long timeLeftInMillis= 60000;

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

        question = getQuestion();
        if(question == null)
            return;

        answer_index = question.getMcanswer();

        COUNTDOWN_IN_MILLIS = question.getTimelimit() * 1000;   // get the time limit in millis

        // compare the current time to time it was posted
        // if the time difference is less than the timeLimit then there is still time Left
        long timeDifference = System.currentTimeMillis() - question.getTimelaunched();
        if(timeDifference > COUNTDOWN_IN_MILLIS)
            timeLeftInMillis = 0;
        else
            timeLeftInMillis = COUNTDOWN_IN_MILLIS - (System.currentTimeMillis() - question.getTimelaunched());

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
        if(timeLeftInMillis > 0) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(timeLeftInMillis != 0) {
                        timeLeftInMillis = millisUntilFinished;
                        updateCountDownText();
                        // need to set the percentage for the progress bar
                        // 1. Progress Bar only had 100 different settings. 0-100 integers
                        int progress = (int) (100 * timeLeftInMillis / COUNTDOWN_IN_MILLIS);
                        progressBar.setProgress(progress);
                    }
                }
                @Override
                public void onFinish() {
                    timeLeftInMillis = 0;
                    updateCountDownText();
                    countDownTimer.cancel();
                    question.setActive(false);
                    progressBar.clearAnimation();
                    progressBar.setVisibility(View.INVISIBLE);
                    displayAnswer.setVisibility(View.VISIBLE);
                    displayAnswer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAnswer();
                        }
                    });
                }
            }.start();
        }
        else {                          // timeLeftInMillis == 0;
            int progress = 0;
            updateCountDownText();
            progressBar.setProgress(progress);
            progressBar.setVisibility(View.INVISIBLE);
            displayAnswer.setVisibility(View.VISIBLE);
            displayAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAnswer();
                }
            });
        }
    }

    // display the correct answer to the students
    private void showAnswer() {
        if(answer_index == 0)
            rb1.setTextColor(Color.GREEN);
        else if(answer_index == 1)
            rb2.setTextColor(Color.GREEN);
        else if(answer_index == 2)
            rb3.setTextColor(Color.GREEN);
        else if(answer_index == 3)
            rb4.setTextColor(Color.GREEN);

    }

    // update the countdown text for the timer
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
