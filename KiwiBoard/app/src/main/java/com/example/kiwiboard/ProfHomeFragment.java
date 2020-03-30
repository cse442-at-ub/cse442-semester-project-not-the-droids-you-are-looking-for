package com.example.kiwiboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfHomeFragment extends Fragment {

    private RecyclerView activeRecyclerView;
    private RecyclerView recentRecyclerView;
    private QuestionAdapter activeAdapter;
    private QuestionAdapter recentAdapter;
    private ArrayList<Question> questions;
    private ArrayList<Question> activeQuestions;
    private ArrayList<Question> recentQuestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View professorView =  inflater.inflate(R.layout.fragment_professor_home, container,false); // Inflate student_home fragment into StudentMain activity

        // Find views from within root activity StudentMain.java
        activeRecyclerView = (RecyclerView) professorView.findViewById(R.id.rcyProfActiveQuestions);
        recentRecyclerView = (RecyclerView) professorView.findViewById(R.id.rcyQuestionQueue);

        activeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ProfData.setCurrentcourse(0);
        int courseindex = ProfData.getCurrentcourse();
        if (courseindex < 0){
            return professorView;
        }
        Course currentcourse = ProfData.getCourses().get(courseindex);
        questions = currentcourse.getQuestions();
        int numQuestions = questions.size();

        // Refresh question numbers
        Question question;
        for(int i = 0; i < numQuestions; i++){
            question = questions.get(i);
            question.setQuestionnumber(i+1);
            questions.set(i, question);
        }

        activeQuestions = new ArrayList<>();
        recentQuestions = new ArrayList<>();

        activeAdapter = new QuestionAdapter(getActivity(), activeQuestions);
        recentAdapter = new QuestionAdapter(getActivity(), recentQuestions);

        activeRecyclerView.setAdapter(activeAdapter);
        recentRecyclerView.setAdapter(recentAdapter);

        generateActiveQuestionList();
        generateQueue();

        activeAdapter.notifyDataSetChanged();
        recentAdapter.notifyDataSetChanged();

        return professorView;

    }

    private void generateActiveQuestionList() {
        // Get the current active question(s)
        int numQuestions = questions.size();
        if (numQuestions >= 2){
            activeQuestions.add(questions.get(numQuestions - 1));
            activeQuestions.add(questions.get(numQuestions - 2));
        }
    }

    private void generateQueue() {
        // Get the current recent question(s)
        int numQuestions = questions.size();
        if (numQuestions > 2){
            for (int i = numQuestions - 3; i > 0; i--){
                recentQuestions.add(questions.get(i));
            }
        }
    }
}
