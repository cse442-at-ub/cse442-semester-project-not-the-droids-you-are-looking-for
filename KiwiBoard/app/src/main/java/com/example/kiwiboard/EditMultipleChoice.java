package com.example.kiwiboard;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class EditMultipleChoice extends AppCompatActivity {

    private int choicenum = 2;
    private RadioButton[] rb;
    private ImageButton addButton;
    private ImageButton[] del;
    private EditText[] mc;
    private EditText txtDesc, txtpts;

    private ArrayList<Course> courses = ProfData.getCourses();
    private int cindex = ProfData.getCurrentcourse();
    private int qindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_multiple_choice);
        setToolbar("Edit Question");

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

        addButton = (ImageButton) findViewById(R.id.btnaddchoice);

        del = new ImageButton[5];
        del[0] = (ImageButton) findViewById(R.id.btnremovechoice1);
        del[1] = (ImageButton) findViewById(R.id.btnremovechoice2);
        del[2] = (ImageButton) findViewById(R.id.btnremovechoice3);
        del[3] = (ImageButton) findViewById(R.id.btnremovechoice4);
        del[4] = (ImageButton) findViewById(R.id.btnremovechoice5);


        int cindex = ProfData.getCurrentcourse();
        if (cindex < 0) {
            return;
        }
        Course course = ProfData.getCourse(cindex);
        qindex = ProfData.getLastclickedquestion();
        Question question = course.getQueueQuestion(qindex);

        String description = question.getDescription();
        ArrayList<String> choices = question.getChoices();
        int mcanswer = question.getMcanswer();
        double points = question.getMaxpoints();

        txtDesc.setText(description);
        txtpts.setText("".concat(""+points));

        choicenum = choices.size();
        for (int choice = 0; choice < choices.size(); choice++){
            mc[choice].setText(choices.get(choice));
            if(choice == mcanswer){
                rb[choice].setChecked(true);
            }
        }

        // Hide unused RBs
        updateVisibilities();



    }

    public void updateVisibilities(){
      // Show 1 - choicenum
        for (int i = 0; i < choicenum; i++){
            rb[i].setVisibility(View.VISIBLE);
            mc[i].setVisibility(View.VISIBLE);
            if (choicenum > 2)
                del[i].setVisibility(View.VISIBLE);
        }

        if (choicenum < 3){
            del[0].setVisibility(View.GONE);
        del[1].setVisibility(View.GONE);
        }

      // Hide choicenum+1 - 5
        for (int i = choicenum; i < 5; i++){
            rb[i].setVisibility(View.GONE);
            mc[i].setVisibility(View.GONE);
            if (choicenum < 3)
                del[i].setVisibility(View.GONE);
        }

        if (choicenum < 5){
        addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.GONE);
        }

    }

    public void newChoice(View view) {
        boolean filled = true;
        for (int i = 0; i < choicenum; i++){
            if (mc[i].getText().toString().equals("")){
                filled = false;
            }
        }
        if (!filled){
            Toast.makeText(this, "Fill out existing choices first", Toast.LENGTH_SHORT).show();
        } else {
            choicenum += 1;
            updateVisibilities();
        }
    }

    public void deleteChoice1(View view) {
        if (choicenum > 2){
            for(int i = 1; i < choicenum; i++){
                rb[i - 1].setChecked(rb[i].isChecked());
                mc[i - 1].setText(mc[i].getText().toString());
            }
                mc[choicenum - 1].setText("");
                if(rb[choicenum - 1].isChecked()){
                    rb[choicenum - 1].setChecked(false);
                }
                choicenum -= 1;
                updateVisibilities();
        } else{
            Toast.makeText(this, "You must have at least 2 choices", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteChoice2(View view) {
        if (choicenum > 2){
            for(int i = 2; i < choicenum; i++){
                rb[i - 1].setChecked(rb[i].isChecked());
                mc[i - 1].setText(mc[i].getText().toString());
            }
                mc[choicenum - 1].setText("");
                if(rb[choicenum - 1].isChecked()){
                    rb[choicenum - 1].setChecked(false);
                }
                choicenum -= 1;
                updateVisibilities();
        } else{
            Toast.makeText(this, "You must have at least 2 choices", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteChoice3(View view) {
        if (choicenum > 2){
            for(int i = 3; i < choicenum; i++){
                rb[i - 1].setChecked(rb[i].isChecked());
                mc[i - 1].setText(mc[i].getText().toString());
            }
                mc[choicenum - 1].setText("");
                if(rb[choicenum - 1].isChecked()){
                    rb[choicenum - 1].setChecked(false);
                }
                choicenum -= 1;
                updateVisibilities();
        } else{
            Toast.makeText(this, "You must have at least 2 choices", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteChoice4(View view) {
        if (choicenum > 2){
            for(int i = 4; i < choicenum; i++) {
                rb[i - 1].setChecked(rb[i].isChecked());
                mc[i - 1].setText(mc[i].getText().toString());
            }
                mc[choicenum - 1].setText("");
                if(rb[choicenum - 1].isChecked()){
                    rb[choicenum - 1].setChecked(false);
                }
                choicenum -= 1;
                updateVisibilities();
        } else{
            Toast.makeText(this, "You must have at least 2 choices", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteChoice5(View view) {
        if (choicenum > 2){
                mc[choicenum - 1].setText("");
                if(rb[choicenum - 1].isChecked()){
                    rb[choicenum - 1].setChecked(false);
                }
                choicenum -= 1;
                updateVisibilities();
        } else{
            Toast.makeText(this, "You must have at least 2 choices", Toast.LENGTH_SHORT).show();
        }
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

        boolean filled = true;
        for (int i = 0; i < choicenum; i++){
            if (mc[i].getText().toString().equals("")){
                filled = false;
            }
        }
        if (!filled){
            Toast.makeText(this, "The answer field is empty", Toast.LENGTH_SHORT).show();
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

        course.setQueueQuestion(qindex, question);
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
