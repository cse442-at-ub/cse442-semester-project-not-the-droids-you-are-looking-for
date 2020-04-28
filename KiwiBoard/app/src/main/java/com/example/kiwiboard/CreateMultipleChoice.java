package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateMultipleChoice extends AppCompatActivity {

    private int choicenum = 2;
    private RadioButton[] rb;
    private Button addButton;
    private EditText[] mc;
    private EditText txtDesc, txtpts;

    private ArrayList<Course> courses = ProfData.getCourses();
    private int cindex = ProfData.getCurrentcourse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_multiple_choice);
        setToolbar("Create Question");

        // Find all views from the xml
        txtDesc = (EditText) findViewById(R.id.txtprofQuestionInputBox);
        txtpts = (EditText) findViewById(R.id.txttotalPoints);

        rb = new RadioButton[5];
        rb[0] = (RadioButton) findViewById(R.id.rb1);
        rb[1] = (RadioButton) findViewById(R.id.rb2);
        rb[2] = (RadioButton) findViewById(R.id.rb3);
        rb[3] = (RadioButton) findViewById(R.id.rb4);
        rb[4] = (RadioButton) findViewById(R.id.rb5);

        mc = new EditText[5];
        mc[0] = (EditText) findViewById(R.id.txtChoice1);
        mc[1] = (EditText) findViewById(R.id.txtChoice2);
        mc[2] = (EditText) findViewById(R.id.txtChoice3);
        mc[3] = (EditText) findViewById(R.id.txtChoice4);
        mc[4] = (EditText) findViewById(R.id.txtChoice5);

        addButton = (Button) findViewById(R.id.btnaddchoice);

        // Hide rbs 3 through 5 by default
        updateVisibilities();



    }

    public void updateVisibilities(){
      // Show 1 - choicenum
        for (int i = 0; i < choicenum; i++){
            rb[i].setVisibility(View.VISIBLE);
        }

      // Hide choicenum+1 - 5
        for (int i = choicenum; i < 5; i++){
            rb[i].setVisibility(View.GONE);
        }

        if (choicenum < 5){
        addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.GONE);
        }

    }

    public void newChoice(View view) {
        choicenum += 1;
        updateVisibilities();
    }

    public void submit(View view) {
        String description = txtDesc.getText().toString();
        String textpoints = txtpts.getText().toString();
        if (description.equals("")){
            Toast.makeText(this, "Enter a description", Toast.LENGTH_SHORT).show();
            return;
        }
        double points;
        if (isDouble(textpoints)){
            points = Double.parseDouble(textpoints);
        } else {
            Toast.makeText(this, "Enter a valid number of points", Toast.LENGTH_SHORT).show();
            return;
        }
        int mcanswer = -1;
        String choicetext;
        ArrayList<String> choices = new ArrayList<>();
        for (int i = 0; i < choicenum; i++){
            if (rb[i].isChecked())
                mcanswer = i;
            choicetext = mc[i].getText().toString();
            if (!choicetext.equals(""))
                choices.add(choicetext);
        }
        if (mcanswer == -1){
            Toast.makeText(this, "Select an answer", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cindex < 0){
            Toast.makeText(this, "You must first select a course!", Toast.LENGTH_SHORT).show();
            return;
        }
        Course course = courses.get(cindex);

        Question question = new Question(Question.QuestionType.MULTIPLECHOICE, description);
        question.setChoices(choices);
        question.setMcanswer(mcanswer);
        question.setPointsreceived(points);
        question.setActive(false);
        question.setInQueue(true);

        course.addQueueQuestion(question);
        ProfData.setCourse(cindex, course);

        Toast.makeText(this, "Question added to queue", Toast.LENGTH_SHORT).show();
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

    public static boolean isInteger(String string) {
        if (string == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(string);
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

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public void setToolbar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profMultChoice_toolbar);

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
