package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfMCQuestionView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_mc_question_view);

        Question question = getQuestion();
        if (question == null) {
            return;
        }

        ((TextView) findViewById(R.id.Prof_Quest_View_Answers_TextView)).setText(question.getDescription());

        if(question.getChoices() != null) {
            ListView devin = (ListView) findViewById(R.id.Prof_Quest_View_List);
            ArrayAdapter bag = new ArrayAdapter(this, android.R.layout.simple_list_item_1, question.getChoices());
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
