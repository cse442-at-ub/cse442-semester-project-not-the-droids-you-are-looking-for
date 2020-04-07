package com.example.kiwiboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.TextView;


public class StudentQuestionLogFragment extends Fragment {
    private ListView listView;
    private ArrayList<Question> questions;
    private ArrayAdapter<String> arrayAdapter;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rosterview = inflater.inflate(R.layout.fragment_student_question_log,container,false);
        listView = rosterview.findViewById(R.id.qLog_ListView);

        // Get the current course and declare variables
        int courseindex = StudentData.getCurrentcourse();
        /*
        if(courseindex < 0) {
            noCourseSelected(rosterview); // if no course was selected, display message to user to join a course
            return rosterview;
        }
        */

        Course currentcourse = StudentData.getCourses().get(courseindex);
        questions = currentcourse.getQuestions();
        int numQuestions = questions.size();
        String questionDescription;

        // Populate String array of student names
        String[] questionArray = new String[numQuestions];
        for (int i = 0; i < numQuestions; i++) {
            questionDescription = "Q" + i + " - " + questions.get(i).getDescription();
            questionArray[i] = questionDescription;
        }
        arrayAdapter = new ArrayAdapter<>(rosterview.getContext(), android.R.layout.simple_list_item_1, questionArray);
        listView.setAdapter(arrayAdapter);
        return rosterview;

    }

    /*
    public void noCourseSelected(View view) {
        String[] message = new String[1];
        message[0] = "You must join a course!";
        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, message);
        listView.setAdapter(arrayAdapter);
    }
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Variable initializations here, excluding View objects
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Initialise View objects here
    }

    // method to return how many questions are in the users list
    public int getNumOfQuestions() {
        return questions.size();
    }



}

