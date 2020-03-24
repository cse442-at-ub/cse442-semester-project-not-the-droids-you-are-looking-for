package com.example.kiwiboard;

import java.util.ArrayList;

public class StudentData {
    
    private static String name;                 // Student's name
    private static String email;                // Student's email
    private static String password;             // Student's password
    private static int currentcourse;           // Student's currently selected course
    private static ArrayList<Course> courses;   // Student's courses

    // Default constructor
    public StudentData(){
        StudentData.name = name;
        StudentData.email = email;
        StudentData.password = password;
        StudentData.currentcourse = -1;
        StudentData.courses = new ArrayList<>();
    }



    // Clears all student data
    public void clearAllData(){
        name = "";
        email = "";
        password = "";
        currentcourse = -1;
        courses = new ArrayList<Course>();
    }

    // Adds a new course from an existing Course object
    public void addCourse(Course course){
        courses.add(course);
    }

    // Adds a new course using Course's default constructor
    public void addNewCourse(String courseName, String instructorname, int classKey, ArrayList<Question> questions, ArrayList<Student> students){
        courses.add(new Course(courseName, instructorname, classKey, questions, students));
    }

    // Removes the course at the index. Indices start at 0.
    public void removeCourse(int index){
        courses.remove(index);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        StudentData.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        StudentData.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StudentData.password = password;
    }

    public static int getCurrentcourse() {
        return currentcourse;
    }

    public static void setCurrentcourse(int currentcourse) {
        StudentData.currentcourse = currentcourse;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void setCourses(ArrayList<Course> courses) {
        StudentData.courses = courses;
    }
}
