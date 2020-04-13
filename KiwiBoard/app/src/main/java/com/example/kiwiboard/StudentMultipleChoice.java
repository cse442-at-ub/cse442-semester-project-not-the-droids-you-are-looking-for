package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Locale;

public class StudentMultipleChoice extends AppCompatActivity {
    // Timer Variables
    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private ColorStateList textColorDefaultCd;  // Want to change textColor when less than 10 secs remains
    private ProgressBar progressBar;
    private TextView timerText;
    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button submitButton;
    private ColorStateList textColorDefaultRb;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private long backPressedTime;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_multiple_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.multChoice_toolbar) ;
        setToolbar("Multiple Choice", toolbar);

        timerText = findViewById(R.id.txt_countdown);
        radioGroup = findViewById(R.id.multipleChoiceOptions);
        rb1 = findViewById(R.id.multchoice1);
        rb2 = findViewById(R.id.multchoice2);
        rb3 = findViewById(R.id.multchoice3);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = timerText.getTextColors();

        progressBar.setVisibility(View.VISIBLE);

        // dbHelper is not coded yet
        // immediately want to start timer when we display a new question
        displayQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void displayQuestion() {
        //rb1.setTextColor(textColorDefaultRb);
        //rb2.setTextColor(textColorDefaultRb);
        //rb3.setTextColor(textColorDefaultRb);
        //rb4.setTextColor(textColorDefaultRb);
        radioGroup.clearCheck();

        // code to set answer options will go here later

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startTimer();
    }

    private void startTimer() {
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
            timerText.setTextColor(Color.RED);
        }
        else {
            //timerText.setTextColor(textColorDefaultCd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        }
        else {
            Toast.makeText(this, "Press back twice quickly to finish",
                    Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
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
        timerText.setText(time);
    }

}
