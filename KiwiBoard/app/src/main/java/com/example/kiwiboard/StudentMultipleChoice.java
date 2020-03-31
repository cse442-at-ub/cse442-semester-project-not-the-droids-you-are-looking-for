package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Locale;

public class StudentMultipleChoice extends AppCompatActivity {
    private ProgressBar progressBar;
    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView timerText;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_multiple_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.multChoice_toolbar);
        timerText = findViewById(R.id.txt_countdown);
        setToolbar("Multiple Choice", toolbar);

        this.progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //startTimer();
                progressBar.setVisibility(View.GONE);
            }
        }, 200000);
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                if(timeLeftInMillis == 0) {
                    countDownTimer.cancel();
                }
            }
            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        // "%02d:%02d" : time string that looks like a clock
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);
        editor.apply();

        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        timeLeftInMillis = preferences.getLong("millisLeft", START_TIME_IN_MILLIS);
        timerRunning = preferences.getBoolean("timerRunning", false);
        updateCountDownText();

        if(timerRunning) {
            endTime = preferences.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if(timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                timerRunning = false;
                updateCountDownText();
            }
            else
                startTimer();
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
        timerText.setText(time);
    }

}
