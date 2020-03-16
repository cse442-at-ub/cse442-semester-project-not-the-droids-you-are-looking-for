package com.example.kiwiboard;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;

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

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMAXCHOICES() {
        return MAXCHOICES;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getQuestionnumber() {
        return questionnumber;
    }

    public void setQuestionnumber(int questionnumber) {
        this.questionnumber = questionnumber;
    }

    public double getPointsreceived() {
        return pointsreceived;
    }

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
                if (textresponse.length() > 0){
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