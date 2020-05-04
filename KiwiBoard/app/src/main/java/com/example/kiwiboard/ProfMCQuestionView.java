package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfMCQuestionView extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_mc_question_view);

        Question question = getQuestion();
        if (question == null) {
            return;
        }
        toolbar = (Toolbar) findViewById(R.id.Prof_MC_Quest_Toolbar);
        toolbar.setTitle("Question Log: Question "+ (ProfData.getLastclickedquestion() + 1));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.Prof_Quest_View_Answers_TextView)).setText(question.getDescription());


        if(question.getChoices() != null) {

            String[] choices = new String[question.getChoices().size()];
            int i = 0;
            for(String choice : question.getChoices()){
                choices[i] = choice;
                i++;
            }

            ListView devin = (ListView) findViewById(R.id.Prof_Quest_View_List);
            ArrayAdapter bag = new ArrayAdapter(this, android.R.layout.simple_list_item_1, choices);
            devin.setAdapter(bag);
        }
    }

    private Question getQuestion(){
        int courseindex = ProfData.getCurrentcourse();
        if(courseindex < 0) {
            return null;
        }
        Course currentcourse = ProfData.getCourses().get(courseindex);

        int qindex = ProfData.getLastclickedquestion();
        if (qindex < 0)
            return null;
        ArrayList<Question> questions = currentcourse.getQuestions();
        return questions.get(qindex);
    }

}
