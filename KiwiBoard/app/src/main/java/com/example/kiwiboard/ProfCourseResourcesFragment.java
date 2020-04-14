package com.example.kiwiboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfCourseResourcesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profView = inflater.inflate(R.layout.fragment_prof_course_resources, container, false);


        TextView courseDes = (TextView) profView.findViewById(R.id.CourseDescritionText);
        TextView courseID = (TextView) profView.findViewById(R.id.CourseTitle);
        // Get current course
        int courseIndex = ProfData.getCurrentcourse();
        if (courseIndex == -1) {
            return profView;
        }

        final Course currentCourse = ProfData.getCourses().get(courseIndex);

        // Recieve all the sitenames and urls For the current course

        ArrayList<String> urls = currentCourse.getUrls();
        ArrayList<String> sitenames = currentCourse.getSitenames();

        // set course text for the ID and get the description of the current course
        courseDes.setText(currentCourse.getDescription());
        courseID.setText(currentCourse.getCourseName());

        //create list view for necessary links

        ListView linkList = (ListView) profView.findViewById(R.id.UsefulLinks);

        //Create adapter to load stuff into listView

        ArrayAdapter<String> linkListAdapter = new ArrayAdapter<String>(profView.getContext(), R.layout.course_item_format, sitenames);

        linkList.setAdapter(linkListAdapter);

        linkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int currentcourse = ProfData.getCurrentcourse();
                Course course = ProfData.getCourses().get(currentcourse);

                String url = course.getURL(arg2);

                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

//                ProfMain profMain = (ProfMain) getActivity();
//                profMain.setToolbarText(coursetext);
//                profMain.setDrawerCourse(coursetext);
            }
        });

        return profView;
    }
}
