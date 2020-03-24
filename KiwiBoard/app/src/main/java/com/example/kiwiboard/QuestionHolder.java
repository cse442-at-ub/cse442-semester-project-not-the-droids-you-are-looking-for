package com.example.kiwiboard;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionHolder extends RecyclerView.ViewHolder {
    private TextView txtNumber, txtText;
    private Context activityContext;

    public QuestionHolder(View itemView, Context context) {
        super(itemView);
        txtNumber = itemView.findViewById(R.id.txtqnumpreview);
        txtText = itemView.findViewById(R.id.txtqtextpreview);
        activityContext = context;
    }

    public void setQuestionDetails(final Question question) {

        txtNumber.setText("Q" + question.getQuestionnumber());
        txtText.setText(question.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnum = question.getQuestionnumber();
                int qindex = qnum - 1;
                StudentData.setLastclickedquestion(qindex);

                Question.QuestionType type = question.getType();

                if (type == Question.QuestionType.MULTIPLECHOICE) {
                    activityContext.startActivity(new Intent(activityContext, MultipleChoice.class));
                }
                else if (type == Question.QuestionType.SHORTANSWER) {
                    activityContext.startActivity(new Intent(activityContext, ShortAnswer.class));
                }
            }
        });
    }
}
