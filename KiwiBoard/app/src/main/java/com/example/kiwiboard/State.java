package com.example.kiwiboard;

public class State {
    public static int question_id;
    public static Question question;
    public static int course;
    public  State(Question q, int id, int c_num){
        State.question_id = id;
        State.question = q;
        State.course = c_num;
    }
    public State(){}
}
