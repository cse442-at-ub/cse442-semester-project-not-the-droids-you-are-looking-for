package com.example.kiwiboard;

import java.util.ArrayList;

class Student {
    private String name;
    private String email;
    private ArrayList<Question> questions;

    public Student(String name, String email, ArrayList<Question> questions) {
        this.name = name;
        this.email = email;
        this.questions = questions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
