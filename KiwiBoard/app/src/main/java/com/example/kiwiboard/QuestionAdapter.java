package com.example.kiwiboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

    private Context context;

    private ArrayList<Question> questions;
    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_preview, parent, false);
        return new QuestionHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question question = questions.get(position);
        holder.setQuestionDetails(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
