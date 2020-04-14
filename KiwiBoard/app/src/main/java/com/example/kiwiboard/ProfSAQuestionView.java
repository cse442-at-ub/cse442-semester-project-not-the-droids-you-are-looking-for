package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfSAQuestionView extends AppCompatActivity {
    private TextView answer;
    private TextView description;
    private Question question;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_sa_question_view);

        toolbar = (Toolbar) findViewById(R.id.Prof_SA_Quest_Toolbar);
        toolbar.setTitle("Question Log: Question " + (ProfData.getLastclickedquestion() + 1));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        question = getQuestion();

        answer = (TextView) findViewById(R.id.Prof_SA_Quest_View_Answer_TXT);
        answer.setText(question.getTextanswer());

        description = (TextView) findViewById(R.id.Prof_SA_Quest_View_Desp_TXT);
        description.setText(question.getDescription());
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
