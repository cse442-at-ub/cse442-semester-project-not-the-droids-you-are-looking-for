package com.example.kiwiboard;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class StudentShortAnswer extends AppCompatActivity {
    private ArrayList<Question> questions;
    private Question question;
    private Course currentCourse;
    private ProgressBar progressBar;
    private TextView txtCountdownText;
    private TextView questionNumber;
    private TextView questionDescription;
    private EditText answer;
    private Button submitButton;
    // Timer Variables
    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_short_answer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.shortAnswer_toolbar);
        setToolbar("Short Answer", toolbar);

        questionNumber = findViewById(R.id.SA_questionNumber);
        txtCountdownText = findViewById(R.id.SA_txt_countdown);
        progressBar = findViewById(R.id.SA_progressBar);
        answer = (EditText) findViewById(R.id.SA_txt_answer);
        submitButton = findViewById(R.id.SA_submitButton);
        questionDescription = findViewById(R.id.SA_questionTextView);

        int course_index = StudentData.getCurrentcourse();
        currentCourse = StudentData.getCourses().get(course_index);
        questions = currentCourse.getQuestions();
        //progressBar.setVisibility(View.VISIBLE);
        displayQuestion();
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
        int questionIndex;
        if(courseindex < 0) {
            return null;
        }
        currentCourse = StudentData.getCourses().get(courseindex);
        questionIndex = StudentData.getLastclickedquestion();
        if (questionIndex < 0)
            return null;
        return questions.get(questionIndex);
    }


    private void displayQuestion() {
        question = getQuestion();
        if(question == null) {
            return;
        }
        questionDescription.setText(question.getDescription());     // set text for question description
        questionNumber.setText("#" + question.getQuestionnumber());     // set text for question number
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;


        if(question.isLaunched()) {
            // compare the current time to time it was posted
            // if the time difference is less than the timeLimit then there is still time Left
            long timeDifference = System.currentTimeMillis() - question.getTimelaunched();
            if(timeDifference > COUNTDOWN_IN_MILLIS)
                timeLeftInMillis = 0;
            else
                timeLeftInMillis = COUNTDOWN_IN_MILLIS - (System.currentTimeMillis() - question.getTimelaunched());
            answer.setText(question.getTextresponse());
        }
        else {
            question.setLaunched(true);
            question.setTimelaunched(System.currentTimeMillis());
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        }
        startTimer();
    }

    private void startTimer() {
        if(timeLeftInMillis > 0) {
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
                    question.setActive(false);
                    updateCountDownText();
                    countDownTimer.cancel();
                    progressBar.clearAnimation();
                    switch_to_main();


                    // checkAnswer will lock in the answer selected when time runs out
                    // when coded cancel the countDownTimer inside of it
                    // checkAnswer();
                }
            }.start();
        }
        else {
            int progress = 0;
            timeLeftInMillis = 0;
            updateCountDownText();
            progressBar.setProgress(progress);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // only get remaining after dividing by 60
        // "%02d:%02d" : time string that looks like a clock
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        setTimerText(timeLeftFormatted);

        if(timeLeftInMillis < 10000) {
            txtCountdownText.setTextColor(Color.RED);
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

    public void setTimerText(String time) {
        txtCountdownText.setText(time);
    }


    //public void set_description(){ txt_box.setText(question.getDescription()); }
    /*
     *   public void switch_to_main(View view)
     *   This function will be used switch from the short Activity and store the current students
     *   answer in a question object
     *
     */


    public void switch_to_main(){
        String result = collect_answer();
        int index_c = StudentData.getCurrentcourse(), index_q = StudentData.getLastclickedquestion();

        if(result.compareTo("") == 0){
            Toast.makeText(getApplicationContext(),"Please fill in an answer.",Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getApplicationContext(),"Submitting Answer!",Toast.LENGTH_SHORT).show();

        Course course = StudentData.getCourse(index_c);
        Question question = course.getQuestion(index_q);
        question.setTextresponse(result);

        course.setQuestion(index_q,question);
        StudentData.setCourse(index_c,course);

        this.finish();
    }

    public void cover_switch_to_main(View view){
        switch_to_main();
    }

    /*
     *  public void clear_txt_anwser(View view)
     *  This function will delete all text in the txt_answer
     *
     *  Credit: https://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered
     *  used to derive line 26

    public void clear_txt_answer(View view) { try { answer_view.getText().clear(); } catch (Exception e){} }
*/
    // collect the text stored in txt_answer as a string
    public String collect_answer() { return answer.getText().toString();}

}
