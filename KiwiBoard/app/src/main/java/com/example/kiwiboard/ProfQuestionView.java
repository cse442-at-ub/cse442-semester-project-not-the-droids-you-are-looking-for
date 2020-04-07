package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProfQuestionView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_question_view);
        if(State.question == null)
            return;
        ((TextView) findViewById(R.id.Prof_Quest_View_Answers_TextView)).setText(State.question.getDescription());

        if(State.question.getChoices() == null)
            return;
        ListView devin = (ListView) findViewById(R.id.Prof_Quest_View_List);
        ArrayAdapter bag = new ArrayAdapter(this,android.R.layout.simple_list_item_1,State.question.getChoices());
        devin.setAdapter(bag);
    }
}
