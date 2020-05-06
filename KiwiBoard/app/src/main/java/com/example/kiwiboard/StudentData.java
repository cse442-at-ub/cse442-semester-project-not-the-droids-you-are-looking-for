package com.example.kiwiboard;

import java.util.ArrayList;

public class StudentData {
    
    private static String name;                 // Student's name
    private static String ID;                   // Student's ID
    private static String email;                // Student's email
    private static String password;             // Student's password
    private static int currentcourse = -1;           // Student's currently selected course
    private static int lastclickedquestion = -1;     // The most recently selected question index
    private static boolean studentmode = false;      // Flag to track when student mode is entered
    private static ArrayList<Course> courses;   // Student's courses
    private static String university;

    // Default constructor
    public StudentData(){
        StudentData.name = name;
        StudentData.ID = ID;
        StudentData.email = email;
        StudentData.password = password;
        StudentData.currentcourse = -1;
        StudentData.courses = new ArrayList<>();
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        StudentData.name = name;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        StudentData.ID = ID;
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

    public static String getUniversity() {
        return university;
    }

    public static void setUniversity(String university) {
        StudentData.university = university;
    }

    public static boolean isStudentmode() {
        return studentmode;
    }

    public static void setStudentmode(boolean mode) {
        if (mode)
            ProfData.setProfessormode(false);

        StudentData.studentmode = mode;
    }

    public static int getLastclickedquestion() {
        return lastclickedquestion;
    }

    public static void setLastclickedquestion(int lastclickedquestion) {
        StudentData.lastclickedquestion = lastclickedquestion;
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

    // Adds a new course from an existing Course object
    public static void addCourse(Course course){
        courses.add(course);
    }

    // Adds a new course using Course's default constructor
    public static void addNewCourse(String courseName, String instructorname, int classKey, ArrayList<Question> questions, ArrayList<Student> students){
        courses.add(new Course(courseName, instructorname, classKey, questions, students));
    }

    // Set the course at the index given
    public static void setCourse(int index, Course course){
        courses.set(index, course);
    }

    // Set the course at the index given
    public static Course getCourse(int index){
        return courses.get(index);
    }

    // Removes the course at the index. Indices start at 0.
    public static void removeCourse(int index){
        courses.remove(index);
        if(index == currentcourse){
            currentcourse = -1;
        }
    }

    // Clears all student data
    public static void clearAllData(){
        name = "";
        email = "";
        password = "";
        university = "";
        currentcourse = -1;
        lastclickedquestion = -1;
        courses = new ArrayList<Course>();

    }
}
