package com.example.kiwiboard;

import java.util.ArrayList;

class Course {
    private String courseName;                  // Name of course
    private String instructorname;              // Name of instructor
    private int classKey;                       // Class key
    private ArrayList<Question> questions;      // Question list
    private ArrayList<Student> students;        // Student list. Null for students.

    // Default constructor with parameters
    public Course(String courseName, String instructorname, int classKey, ArrayList<Question> questions, ArrayList<Student> students) {
        this.courseName = courseName;
        this.instructorname = instructorname;
        this.classKey = classKey;
        this.questions = questions;
        this.students = students;
    }

    // Adds a new question from an existing Question object
    public void addQuestion(Question question){
        questions.add(question);
    }

    // Adds a new question using Question's default constructor with parameters
    public void addNewQuestion(Question.QuestionType type, String description, ArrayList<String> choices, int questionnumber, double pointsreceived, int maxpoints, int mcanswer, double numericanswer, String textanswer, ArrayList<Integer> multipleanswers, int mcresponse, ArrayList<Integer> multipleresponses, String textresponse, double numericresponse){
        questions.add(new Question(type, description, choices, questionnumber, pointsreceived, maxpoints, mcanswer, numericanswer, textanswer, multipleanswers, mcresponse, multipleresponses, textresponse, numericresponse));
    }

    // Removes a question at a particular index. Indices start at 0
    public void removeQuestion(int index){
        questions.remove(index);
    }

    public void assignQuestionNumbers(){
        Question q;
        for (int i = 0; i < questions.size(); i++){
            q = questions.get(i);
            q.setQuestionnumber(i+1);
            questions.set(i, q);
        }
    }

    public ArrayList<Double> calculateAverages() {
        ArrayList<Double>averages = new ArrayList<>();

        int numQuestions = questions.size();
        int numStudents = students.size();
        double sum, grade, avg, total;
        Question question;
        Student student;

        for(int i=0;i < numQuestions;i++)
        {
            sum=0;
            for(int j=0;j<numStudents;j++)
            {
                student=students.get(j);
                question=student.getQuestions().get(i);
                grade=question.calculateScore();
                sum+=grade;
            }
            total=(questions.get(i).getMaxpoints())*numStudents;
            if(total==0)
            {
                avg = 0;
            }
            avg=sum/total;
            averages.add(avg);
        }
        return averages;
    }

    public double calculateClassAverage(){
        double sum=0,maxSum=0;
        int numQuestions=questions.size();
        ArrayList<Double>averages=calculateAverages();
        for(int i=0;i<numQuestions;i++)
        {
            sum+=averages.get(i);
            maxSum=questions.get(i).getMaxpoints();
        }
        return sum/maxSum*100;
    }



    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorname() {
        return instructorname;
    }

    public void setInstructorname(String instructorname) {
        this.instructorname = instructorname;
    }

    public int getClassKey() {
        return classKey;
    }

    public void setClassKey(int classKey) {
        this.classKey = classKey;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> Students) {
        this.students = Students;
    }

    public int getNumberOfStudents(){
      return students.size();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
