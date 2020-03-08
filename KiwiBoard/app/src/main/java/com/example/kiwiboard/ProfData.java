package com.example.kiwiboard;

import java.util.ArrayList;

public class ProfData {

    private static String name;                 // Professor's name
    private static String email;                // Professor's email
    private static String password;             // Professor's password
    private static int currentcourse;           // Professor's current course
    private static ArrayList<Course> courses;   // Professor's currently selected course

    // Default constructor with parameters
    public ProfData(String name, String email, int currentcourse, ArrayList<Course> courses) {
        ProfData.name = name;
        ProfData.email = email;
        ProfData.password = password;
        ProfData.currentcourse = currentcourse;
        ProfData.courses = courses;
    }

    // Clears all professor data
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

    // Adds a new course using Course's default constructor with parameters
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
        ProfData.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        ProfData.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ProfData.password = password;
    }

    public static int getCurrentcourse() {
        return currentcourse;
    }

    public static void setCurrentcourse(int currentcourse) {
        ProfData.currentcourse = currentcourse;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void setCourses(ArrayList<Course> courses) {
        ProfData.courses = courses;
    }
}
