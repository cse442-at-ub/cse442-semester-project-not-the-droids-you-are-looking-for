package com.example.kiwiboard;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
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
    private int answer_index;
    private int submission_index;       // the index of the radio button that was clicked and submitted
    private Course currentCourse;
    private int courseIndex;
    private int questionIndex;
    private ProgressBar progressBar;    // the circular progress bar that will surround the countdown text
    private TextView txt_timerText;
    private TextView txt_questionNumber;
    private TextView txt_questionDescription;
    private RadioGroup radioGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    private Button submitButton;
    // Timer Variables
    private static final long COUNTDOWN_IN_MILLIS = 20000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;           // how much time is left
    private long endTime;                    // what time does the timer run out
    private boolean timerRunning;            // is the timer running
    private Question question;               // the question that is clicked
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_multiple_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.multChoice_toolbar) ;
        setToolbar("Multiple Choice", toolbar);

        txt_questionNumber = findViewById(R.id.questionNumber);
        txt_timerText = findViewById(R.id.txt_countdown);
        radioGroup = findViewById(R.id.multipleChoiceOptions);
        rb1 = findViewById(R.id.multchoice1);
        rb2 = findViewById(R.id.multchoice2);
        rb3 = findViewById(R.id.multchoice3);
        rb4 = findViewById(R.id.multchoice4);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        courseIndex = StudentData.getCurrentcourse();
        currentCourse = StudentData.getCourses().get(courseIndex);
        questions = currentCourse.getQuestions();
        choices = questions.get(courseIndex).getChoices();
        progressBar.setVisibility(View.VISIBLE);
        displayQuestion();

        // if the student has already entered a submission
        if(question.getSubmissionEntered()) {
            // click the answer they submitted
            int submission_id = question.getMcresponse();
            if(submission_id == 1) {
                rb1.setChecked(true);
            }
            else if(submission_id == 2) {
                rb2.setChecked(true);
            }
            else if(submission_id == 3) {
                rb3.setChecked(true);
            }
            else if(submission_id == 4) {
                rb4.setChecked(true);
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked()) {
                    question.setSubmissionEntered(true);
                    submission_index = storeSubmission();
                    question = getQuestion();
                    if(question != null) {
                        question.setMcresponse(submission_index);         // the students mcresponse is their submission index
                    }
                    Toast.makeText(getApplicationContext(), "Submitted Choice " + submission_index,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Must select an answer!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        this.questionIndex = StudentData.getLastclickedquestion();
        if (this.questionIndex < 0)
            return null;
        ArrayList<Question> questions = currentCourse.getQuestions();
        return questions.get(this.questionIndex);
    }

    private void displayQuestion() {
        radioGroup.clearCheck();

        //question.setActive(true);
        question = getQuestion();
        answer_index = question.getMcanswer();
        if(question == null) {
            return;
        }

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

            rb1.setText(choice1);
            rb2.setText(choice2);
            rb3.setText(choice3);
            rb4.setText(choice4);
        }

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startTimer();
    }

    private void startTimer() {
        //endTime = System.currentTimeMillis() + timeLeftInMillis;
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
                //timerRunning = false;
                timeLeftInMillis = 0;
                updateCountDownText();
                countDownTimer.cancel();
                progressBar.clearAnimation();
                progressBar.setVisibility(View.INVISIBLE);
                // need to show the student the correct answer
                // if student clicked a different one make the text
                showAnswer();
            }

        }.start();

        timerRunning = true;
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

    // return the id of the clicked radio button.
    private int storeSubmission() {
        int radioID = 0;
        if(rb1.isChecked()) {
            radioID = 1;
        }
        else if(rb2.isChecked()) {
            radioID = 2;
        }
        else if(rb3.isChecked()) {
            radioID = 3;
        }
        else if(rb4.isChecked()) {
            radioID = 4;
        }
        return radioID;
    }

    private void showAnswer() {
        // don't accept anymore submissions after answer is displayed
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submission_index == 0) {
                    Toast.makeText(getApplicationContext(), "Time's up! Submit before timer runs out!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    submitButton.setClickable(false);
                }
            }
        });
        if(question != null) {
            question.setActive(false);      // question is no longer active
        }
        // student never hit submit
        // Check if any radio buttons were checked so they can still get credit if they had right answer selected
        if(submission_index == 0) {
            if(rb1.isChecked()) {
                submission_index = 1;
            }
            else if(rb2.isChecked()) {
                submission_index = 2;
            }
            else if(rb3.isChecked()) {
                submission_index = 3;
            }
            else if(rb4.isChecked()) {
                submission_index = 4;
            }
        }

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

        // if the submission index matches the correct answer index
        if((submission_index - 1) == answer_index) {
            Toast.makeText(this, "Correct!",
                    Toast.LENGTH_SHORT).show();
        }
        // if the submission index is 0 because no answer was submitted
        else if(submission_index == 0) {
            Toast.makeText(this, "Time's up! No submission entered",
                    Toast.LENGTH_SHORT).show();
        }
        // if there was a submission but the index does not match the correct answer index
        else {
            if((submission_index - 1) == 0) {
                rb1.setTextColor(Color.RED);
            }
            else if((submission_index - 1) == 1) {
                rb2.setTextColor(Color.RED);
            }
            else if((submission_index - 1) == 2) {
                rb3.setTextColor(Color.RED);
            }
            else if((submission_index - 1) == 3) {
                rb4.setTextColor(Color.RED);
            }
            Toast.makeText(this, "Incorrect",
                    Toast.LENGTH_SHORT).show();
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
