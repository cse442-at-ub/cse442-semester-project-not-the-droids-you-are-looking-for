package com.example.kiwiboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.Build;
import android.widget.TextView;


public class QuestionLogFragment extends Fragment {
    private ListView listView;
    private ArrayList<Question> questions;
    private ArrayAdapter<String> questionArrayAdapter;
    private TextView tv;
    // new ArrayAdapter<Question>(this,
    //            android.R.layout.simple_list_item_1, questions)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rosterview = inflater.inflate(R.layout.fragment_question_log,container,false);
        listView = rosterview.findViewById(R.id.qLog_ListView);

        // Get the current course and declare variables
        int courseindex = StudentData.getCurrentcourse();
        Course currentcourse = StudentData.getCourses().get(courseindex);
        questions = currentcourse.getQuestions();
        int numQuestions = questions.size();
        String questionDescription;

        // Populate String array of student names
        String[] questionArray = new String[numQuestions];
        for (int i = 0; i < numQuestions; i++) {
            questionDescription = questions.get(i).getDescription();
            questionArray[i] = questionDescription;
        }

        questionArrayAdapter = new ArrayAdapter<>(rosterview.getContext(), android.R.layout.simple_list_item_1, questionArray);
        listView.setAdapter(questionArrayAdapter);
        return rosterview;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Variable initializations here, excluding View objects
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Initialise View objects here
    }

}

