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

        if(!question.isInQueue())
            txtNumber.setText("Q" + question.getQuestionnumber());
        txtText.setText(question.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the number of the question and calculate its index
                int qnum = question.getQuestionnumber();
                int qindex = qnum - 1;
                Question.QuestionType type = question.getType(); // Determine the question type

                if (StudentData.isStudentmode()) {
                    StudentData.setLastclickedquestion(qindex); // Notify student data of index
                    if (type == Question.QuestionType.MULTIPLECHOICE) {
                        activityContext.startActivity(new Intent(activityContext, StudentMultipleChoice.class));
                    } else if (type == Question.QuestionType.SHORTANSWER) {
                        activityContext.startActivity(new Intent(activityContext, StudentShortAnswer.class));
                    }
                }
                if (ProfData.isProfessormode()){
                    ProfData.setLastclickedquestion(qindex); // Notify professor data of index

                    if (question.isInQueue()){
                        ProfMain profMain = (ProfMain) activityContext;
                        profMain.openLauncherDialog();
                    } else {
                        if (type == Question.QuestionType.MULTIPLECHOICE) {
                            activityContext.startActivity(new Intent(activityContext, CreateMultipleChoice.class));
                        } else if (type == Question.QuestionType.SHORTANSWER) {
                            activityContext.startActivity(new Intent(activityContext, CreateShortAnswer.class));
                        }
                    }
                }
            }
        });
    }
}
