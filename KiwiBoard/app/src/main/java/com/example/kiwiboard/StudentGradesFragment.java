package com.example.kiwiboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StudentGradesFragment extends Fragment {
    @Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_grades,container,false);

        int courseindex = StudentData.getCurrentcourse();
        // Check if a course has been selected
        if (courseindex < 0){
            return rootView;
        }

        Course currentcourse = StudentData.getCourses().get(courseindex);
        ArrayList<Question> questions = currentcourse.getQuestions();
        Question question;
        double pointsreceived, percentage;
        double maxpoints;
        ArrayList<Double> averages = currentcourse.calculateAverages();

        // Class Average
        TextView avgTotal=(TextView)rootView.findViewById(R.id.txtStuClassavg);
        double Classaverage = currentcourse.calculateClassAverage();
        DecimalFormat dec = new DecimalFormat("#0.00");
        avgTotal.setText(dec.format(Classaverage) + " % ");

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.ic_progress_bar);
        ProgressBar mProgress = (ProgressBar) rootView.findViewById(R.id.prgStuClassavg);
        mProgress.setProgress((int) Classaverage);
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

        // Table layout

        TableLayout gradesTable;
        gradesTable = (TableLayout) rootView.findViewById(R.id.tblStudentGrades);



        for (int i = 0; i < questions.size(); i++) {

            TableRow tr = new TableRow(getActivity());

            tr.setId(i);
            //tr.setBackgroundResource(R.color.colorPrimaryDark);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            question = questions.get(i);
            pointsreceived = question.calculateScore();
            maxpoints = question.getMaxpoints();
            percentage = question.calculatePercentage();

            //TEXTVIEWS********
            TextView qnum = new TextView(getActivity());
            qnum.setText("" + question.getQuestionnumber());
            qnum.setId(i);
            //qnum.setTextColor(Color.WHITE);
            qnum.setTextSize(20);
            qnum.setPadding(20, 5, 80, 5);
            tr.addView(qnum);

            TextView numpoints = new TextView(getActivity());
            numpoints.setText("" + pointsreceived);
            numpoints.setId(i+i);
            //numpoints.setTextColor(Color.WHITE);
            numpoints.setTextSize(20);
            numpoints.setPadding(10, 5, 80, 0);
            tr.addView(numpoints);

            TextView totalpoints = new TextView(getActivity());
            totalpoints.setText("" + maxpoints);
            totalpoints.setId(i+i);
            //totalpoints.setTextColor(Color.WHITE);
            totalpoints.setTextSize(20);
            totalpoints.setPadding(10, 5, 80, 0);
            tr.addView(totalpoints);

            TextView percent = new TextView(getActivity());
            percent.setText("" + percentage);
            percent.setId(i+i);
            //percent.setTextColor(Color.WHITE);
            percent.setTextSize(20);
            percent.setPadding(10, 5, 80, 0);
            tr.addView(percent);


            //ProgressBar tv3 = new ProgressBar(new ContextThemeWrapper(getActivity(), R.style.horizontalProgressSmall), null,0);
            Drawable drawables = res.getDrawable(R.drawable.ic_progress_bar);
            ProgressBar nProgress = new ProgressBar(new ContextThemeWrapper(getActivity(), R.style.horizontalProgressSmall), null,0);
            nProgress.setProgress((int) percentage);
            nProgress.setMax(100); // Maximum Progress
            nProgress.setId(i+i+i);
            nProgress.setProgressDrawable(drawables);
            nProgress.setPadding(25, 20, 5, 10);
            tr.addView(nProgress,265,75);

            gradesTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }


        return rootView;
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