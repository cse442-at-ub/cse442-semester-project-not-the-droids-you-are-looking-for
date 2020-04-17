package com.example.kiwiboard;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.Calendar;

class Question {

    public enum QuestionType{
        SHORTANSWER, MULTIPLECHOICE, FILLINBLANK, SELECTALL, NUMERIC, TRUEFALSE; // Question Types
    }

    // Question attributes
    private QuestionType type;                  // Question type
    private String description;                 // Question description
    private final int MAXCHOICES = 5;           // Max amount of MC and Select All choices
    private double numerictolerance = 0.002;    // Tolerance for double answers
    private ArrayList<String> choices;          // Container for choices
    private int questionnumber;                 // The index of the question
    private boolean inQueue = false;            // Whether the question is in the queue
    private boolean isActive = false;           // Whether the question is active
    private int timelaunched = 0;               // Exact time when the question was launched
    private int timelimit = 0;                  // Amount of time the question lasts

    private double pointsreceived;               // Points received by student. Null for professors.
    private int maxpoints;                       // Max points possible

    // Answers
    private int mcanswer;                        // Field for mc, and T/F answers
    private double numericanswer;                // Field for numeric answers
    private String textanswer;                      // Field for textual answers
    private ArrayList<Integer> multipleanswers;  // Fields for multiple answers

    // Answer submissions from student
    private int mcresponse;                      // The student's multiple choice submission
    private ArrayList<Integer> multipleresponses; // The Student's select all submission
    private String textresponse;                 // The student's textual submission
    private double numericresponse;              // The student's numeric submission


    // Constructor with parameters
    public Question(QuestionType type, String description, ArrayList<String> choices, int questionnumber, double pointsreceived, int maxpoints, int mcanswer, double numericanswer, String textanswer, ArrayList<Integer> multipleanswers, int mcresponse, ArrayList<Integer> multipleresponses, String textresponse, double numericresponse) {
        this.type = type;
        this.description = description;
        this.choices = choices;
        this.questionnumber = questionnumber;
        this.pointsreceived = pointsreceived;
        this.maxpoints = maxpoints;
        this.mcanswer = mcanswer;
        this.numericanswer = numericanswer;
        this.textanswer = textanswer;
        this.multipleanswers = multipleanswers;
        this.mcresponse = mcresponse;
        this.multipleresponses = multipleresponses;
        this.textresponse = textresponse;
        this.numericresponse = numericresponse;
    }

    // Clear all question data
    public void clearQuestion(){
        this.type = null;
        this.description = "";
        this.choices = new ArrayList<String>();
        this.pointsreceived = 0;
        this.maxpoints = 0;
        this.mcanswer = 0;
        this.numericanswer = 0;
        this.textanswer = "";
        this.multipleanswers = new ArrayList<Integer>();
        this.mcresponse = 0;
        this.multipleresponses = new ArrayList<Integer>();
        this.textresponse = null;
        this.numericresponse = 0;
    }

    // Get the question type
    public QuestionType getType() {
        return type;
    }

    // Set the question type
    public void setType(QuestionType type) {
        this.type = type;
    }

    // Get the question description
    public String getDescription() {
        return description;
    }

    // Set the question description
    public void setDescription(String description) {
        this.description = description;
    }

    // Get the max amount of multiple selection choices
    public int getMAXCHOICES() {
        return MAXCHOICES;
    }

    // Get the choices for a multiple choice question
    public ArrayList<String> getChoices() {
        return choices;
    }

    // Set the choices for a multiple choice question
    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    // Get the number of the question
    public int getQuestionnumber() {
        return questionnumber;
    }

    // Set the number of the question
    public void setQuestionnumber(int questionnumber) {
        this.questionnumber = questionnumber;
    }

    // Check if the question is in the queue
    public boolean isInQueue() {
        return inQueue;
    }

    // Set whether the question is in the queue
    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    // Check if the question is active
    public boolean isActive() {
        return isActive;
    }

    // Set whether the question is active
    public void setActive(boolean active) {
        isActive = active;
    }

    // Get the number of points received by the student
    public double getPointsreceived() {
        return pointsreceived;
    }

    // Set the number of points received by the student
    public void setPointsreceived(double pointsreceived) {
        this.pointsreceived = pointsreceived;
    }

    public int getMaxpoints() {
        return maxpoints;
    }

    public void setMaxpoints(int maxpoints) {
        this.maxpoints = maxpoints;
    }

    public int getMcanswer() {
        return mcanswer;
    }

    public void setMcanswer(int mcanswer) {
        this.mcanswer = mcanswer;
    }

    public double getNumericanswer() {
        return numericanswer;
    }

    public void setNumericanswer(double numericanswer) {
        this.numericanswer = numericanswer;
    }

    public String getTextanswer() {
        return textanswer;
    }

    public void setTextanswer(String textanswer) {
        this.textanswer = textanswer;
    }

    public ArrayList<Integer> getMultipleanswers() {
        return multipleanswers;
    }

    public void setMultipleanswers(ArrayList<Integer> multipleanswers) {
        this.multipleanswers = multipleanswers;
    }

    public int getMcresponse() {
        return mcresponse;
    }

    public void setMcresponse(int mcresponse) {
        this.mcresponse = mcresponse;
    }

    public ArrayList<Integer> getmultipleresponses() {
        return multipleresponses;
    }

    public void setmultipleresponses(ArrayList<Integer> multipleresponses) {
        this.multipleresponses = multipleresponses;
    }

    public String getTextresponse() {
        return textresponse;
    }

    public void setTextresponse(String textresponse) {
        this.textresponse = textresponse;
    }

    public double getNumericresponse() {
        return numericresponse;
    }

    public void setNumericresponse(double numericresponse) {
        this.numericresponse = numericresponse;
    }

    public double getNumerictolerance() {
        return numerictolerance;
    }

    public void setNumerictolerance(double numerictolerance) {
        this.numerictolerance = numerictolerance;
    }

    public ArrayList<Integer> getMultipleresponses() {
        return multipleresponses;
    }

    public void setMultipleresponses(ArrayList<Integer> multipleresponses) {
        this.multipleresponses = multipleresponses;
    }

    public int getTimelaunched() {
        return timelaunched;
    }

    public void setTimelaunched(int timelaunched) {
        this.timelaunched = timelaunched;
    }

    public int getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(int timelimit) {
        this.timelimit = timelimit;
    }

    public double calculateScore(){
        switch (this.type){
            case MULTIPLECHOICE:
            case TRUEFALSE:
                if(mcresponse == mcanswer){
                    return maxpoints;
                } else{
                    return 0;
                }
            case SELECTALL:
                int answer, studentresponse;
                int correct = 0;
                int incorrect = 0;
                boolean containschoice;
                for(int i = 0; i < multipleresponses.size(); i++){ // For each student response
                    studentresponse = multipleresponses.get(i);
                    containschoice = false;
                    for(int j = 0; j < multipleanswers.size(); j++){ // Look through all answers
                        answer = multipleanswers.get(j);
                        if (studentresponse == answer){ // If the given response is an answer
                            containschoice = true; // The answers contain this choice
                        }
                    }
                    if (containschoice){
                         correct += 1;
                    }else{
                        incorrect += 1;
                    }
                }
                if (correct - incorrect > 0) {
                    return (correct - incorrect) / multipleanswers.size() * maxpoints;
                }else{
                    return 0;
                }
            case SHORTANSWER:
                if (textresponse != null && textresponse.length() > 0){
                    return maxpoints;
                } else {
                    return 0;
                }
            case NUMERIC:
                if (numericresponse < numericanswer*(1 + numerictolerance) && numericresponse > numericanswer*(1 - numerictolerance)){
                return maxpoints;
                } else {
                return 0;
                }
            case FILLINBLANK:
                if (textresponse.toLowerCase().equals(textanswer.toLowerCase()) ){
                    return maxpoints;
                } else{
                    return 0;
                }
            default:
                return 0;
        }
    }

    public double calculatePercentage(){
        if (maxpoints != 0) {
            return calculateScore() / maxpoints * 100;
        } else{
            return 0;
        }
    }
}
