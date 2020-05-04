package com.example.kiwiboard;

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

public class StudentHomeFragment extends Fragment {

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
        View studentView =  inflater.inflate(R.layout.fragment_student_home, container,false); // Inflate student_home fragment into StudentMain activity
        // Find views from within root activity StudentMain.java
        activeRecyclerView = (RecyclerView) studentView.findViewById(R.id.rcyActiveQuestions);
        recentRecyclerView = (RecyclerView) studentView.findViewById(R.id.rcyRecentQuestions);

        activeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int courseindex = StudentData.getCurrentcourse();
        if (courseindex < 0){
            return studentView;
        }
        Course currentcourse = StudentData.getCourses().get(courseindex);
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

        return studentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        int courseindex = StudentData.getCurrentcourse();
        if (courseindex < 0){
            return;
        }

        generateActiveQuestionList();
        generateRecentQuestionList();

        activeAdapter.notifyDataSetChanged();
        recentAdapter.notifyDataSetChanged();
    }

    private void generateActiveQuestionList() {
        if (activeQuestions != null)
            activeQuestions.clear();

        if (questions != null) {
            // Get the current active question(s)
            for (Question question : questions) {
                if (question.isActive())
                    activeQuestions.add(question);
            }
        }
    }

    private void generateRecentQuestionList() {
        // Get the current recent question(s)
        if (activeQuestions != null)
            recentQuestions.clear();
        int maxrecents = 7; // the maximum number of recent questions diplayed
        int numQuestions;
        if (questions == null){
            numQuestions = 0;
        } else {
            numQuestions = questions.size();
        }
        int numRecentQuestions = 0;
        Question question;
        for(int q = numQuestions - 1; q >= 0; q--){
            question = questions.get(q);
            if (!question.isActive() && numRecentQuestions < maxrecents){
                numRecentQuestions++;
                recentQuestions.add(question);
            }
        }

    }

}
