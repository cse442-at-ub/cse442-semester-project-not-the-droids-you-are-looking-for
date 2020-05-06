package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditShortAnswer extends AppCompatActivity {
    private EditText txtDesc, txtpts, txtAnswer;
    private ArrayList<Course> courses = ProfData.getCourses();
    private int cindex;                 // course index
    private int qindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_short_answer);
        setToolbar("Edit Question");

        // find the textboxes from the XML
        txtDesc = findViewById(R.id.txtprofQuestionInputBox);
        txtpts = findViewById(R.id.txt_SAtotalPoints);
        txtAnswer = findViewById(R.id.txtprofAnswerBox);

        cindex = ProfData.getCurrentcourse();
        if (cindex < 0) {
            return;
        }
        Course course = ProfData.getCourse(cindex);
        qindex = ProfData.getLastclickedquestion();
        Question question = course.getQueueQuestion(qindex);

        String description = question.getDescription();
        String answer = question.getTextanswer();
        double points = question.getMaxpoints();

        txtDesc.setText(description);
        txtpts.setText("".concat(""+points));
        txtAnswer.setText(answer);
        Button button = findViewById(R.id.profeditSASubmitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });

    }

    public void submit(View view) {
        String description = txtDesc.getText().toString();
        String textpoints = txtpts.getText().toString();
        String answer = txtAnswer.getText().toString();

        if (description.equals("")){
            Toast.makeText(this, "Enter a description", Toast.LENGTH_SHORT).show();
            return;
        }
        if(answer.equals("")) {
            Toast.makeText(this, "Enter an answer", Toast.LENGTH_SHORT).show();
            return;
        }
        double points;
        if (isDouble(textpoints)){
            points = Double.parseDouble(textpoints);
        } else {
            Toast.makeText(this, "Enter a valid number of points", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cindex < 0){
            Toast.makeText(this, "You must first select a course!", Toast.LENGTH_SHORT).show();
            return;
        }
        Course course = courses.get(cindex);

        Question question = new Question(Question.QuestionType.SHORTANSWER, description);
        question.setTextanswer(answer);
        question.setMaxpoints((int) points);
        question.setActive(false);
        question.setInQueue(true);

        course.setQueueQuestion(qindex, question);
        ProfData.setCourse(cindex, course);

        Toast.makeText(this, "Question Edit Completed", Toast.LENGTH_SHORT).show();
        finish();
    }

    public static boolean isDouble(String string) {
        if (string == null) {
            return false;
        }
        try {
            double num = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setToolbar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profEditSA_toolbar);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
        params.topMargin = getStatusBarHeight();

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
