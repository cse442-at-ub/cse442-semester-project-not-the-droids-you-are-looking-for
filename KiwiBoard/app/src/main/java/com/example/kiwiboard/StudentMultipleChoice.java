package com.example.kiwiboard;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class StudentMultipleChoice extends AppCompatActivity {
    private ArrayList<Question> questions;
    ArrayList<String> choices;
    private Course currentCourse;
    private int courseIndex;
    private int questionIndex;
    private ProgressBar progressBar;
    private TextView txt_timerText;
    private TextView txt_questionNumber;
    private TextView txt_questionDescription;
    private RadioGroup radioGroup;
    private Button submitButton;
    // Timer Variables
    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long backPressedTime;
    //private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_multiple_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.multChoice_toolbar) ;
        setToolbar("Multiple Choice", toolbar);

        txt_questionNumber = findViewById(R.id.questionNumber);
        txt_timerText = findViewById(R.id.txt_countdown);
        radioGroup = findViewById(R.id.multipleChoiceOptions);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        courseIndex = StudentData.getCurrentcourse();
        currentCourse = StudentData.getCourses().get(courseIndex);
        questions = currentCourse.getQuestions();
        choices = questions.get(courseIndex).getChoices();
        progressBar.setVisibility(View.VISIBLE);
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

    private Question getQuestion() {
        int courseindex = StudentData.getCurrentcourse();
        if(courseindex < 0) {
            return null;
        }
        Course currentcourse = StudentData.getCourses().get(courseindex);
        this.questionIndex = StudentData.getLastclickedquestion();
        if (this.questionIndex < 0)
            return null;
        ArrayList<Question> questions = currentcourse.getQuestions();
        return questions.get(this.questionIndex);
    }

    private void displayQuestion() {
        radioGroup.clearCheck();

        Question question = getQuestion();
        if(question == null) {
            return;
        }

        txt_questionDescription = findViewById(R.id.questionTextView);
        txt_questionDescription.setText(question.getDescription());     // set text for question description
        txt_questionNumber.setText("#" + question.getQuestionnumber());     // set text for question number

        if(question.getChoices() != null) {
            // populate the choices into the radio button texts
            RadioButton rb1 = findViewById(R.id.multchoice1);
            RadioButton rb2 = findViewById(R.id.multchoice2);
            RadioButton rb3 = findViewById(R.id.multchoice3);
            RadioButton rb4 = findViewById(R.id.multchoice4);
            String choice1 = choices.get(0);
            String choice2 = choices.get(1);
            String choice3 = choices.get(2);
            String choice4 = choices.get(3);

            rb1.setText(choice1);
            rb2.setText(choice2);
            rb3.setText(choice3);
            rb4.setText(choice4);
        }

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

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        }
        else {
            Toast.makeText(this, "Quickly press back twice to quit",
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
        txt_timerText.setText(time);
    }

}
