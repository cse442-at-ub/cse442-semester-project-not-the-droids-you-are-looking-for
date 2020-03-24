package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShortAnswer extends AppCompatActivity {
    /*
    * Question question
    * As the name implies this textfield will be used to hold the question being
    * asked by professor
    * */
    private Question question;

    /*
     * EditText answer_view
     * This EditText object will be used to manipulate the answer text field on the GUI xml
     * */
    private EditText answer_view;

    /*
     *  TextView txt_box
     *  This TextView object will be used to manipulate the question text field on the GUI xml
     * */
    private TextView txt_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);
        if(question == null)
            question = new Question(Question.QuestionType.SHORTANSWER,"What is 2 plus 2",null,0,64,10,10,0,null,null,0,null,null,0);

        txt_box = findViewById(R.id.txt_question);
        answer_view = findViewById(R.id.txt_answer);
        set_description();
    }

    public void set_description(){ txt_box.setText(question.getDescription()); }
    /*
    *   public void switch_to_main(View view)
    *   This function will be used switch from the short Activity and store the current students
    *   answer in a question object
    *
     */

    public void switch_to_main(View view){
        String answer = collect_answer();

        Toast toast = Toast.makeText(getApplicationContext(),"Submitting Answer!",Toast.LENGTH_SHORT);
        toast.show();

        System.out.println("Answer Collected:\t" + answer);
        startActivity(new Intent(this, ProfMain.class));

        question.setTextresponse(collect_answer());

        this.finish();
        startActivity(new Intent(ShortAnswer.this, StudentMain.class));
    }


    /*
     *  public void clear_txt_anwser(View view)
     *  This function will delete all text in the txt_answer
     *
     *  Credit: https://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered
     *  used to derive line 26
     */

    public void clear_txt_answer(View view) { try { answer_view.getText().clear(); } catch (Exception e){} }

    /*
     *  public String collect_answer()
     *  returns the text stored in txt_answer as a string
     *
     */
    public String collect_answer(){ return findViewById(R.id.txt_answer).toString(); }
    public Question get_Question(){return question;}
    public void set_Question(Question quest){ question = quest; }

    public void set_title(Question question){((TextView)findViewById(R.id.txt_quest)).setText("Question: " +question.getQuestionnumber());}
    public void set_title(int num){((TextView)findViewById(R.id.txt_quest)).setText("Question: " +num);}
    public void set_title(String data){((TextView)findViewById(R.id.txt_quest)).setText(data);}
}
