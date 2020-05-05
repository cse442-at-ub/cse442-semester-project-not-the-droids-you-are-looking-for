package com.example.kiwiboard;

import com.google.gson.annotations.SerializedName;

public class Parser {
    @SerializedName("id")
    private String id = "";

    @SerializedName("name")
    private String name = "";

    @SerializedName("email")
    private String email = "";

    @SerializedName("password")
    private String password = "";

    @SerializedName("mode")
    private String mode = "";

    @SerializedName("type")
    private String type = "";

    @SerializedName("status")
    private String status = "";

    @SerializedName("amount")
    private String amount = "";

    @SerializedName("results")
    private String results = "";

    @SerializedName("course_name")
    private String course_name = "";

    @SerializedName("course_description")
    private String course_description = "";


    //For Question Parsing
    @SerializedName("points")
    private int points = 0;

    @SerializedName("description")
    private String description ="";

    @SerializedName("sa_answer")
    private String sa_answer="";

    @SerializedName("mc_answer")
    private int mc_answer;

    @SerializedName("log_time")
    private long log_time;

    @SerializedName("total_time")
    private int total_time;

    @SerializedName("num_answer")
    private double num_answer = 0;

    @SerializedName("course_id")
    private String course_id="";

    @SerializedName("num")
    private int num = -1;

    @SerializedName("multi_choice1")
    private String multi_choice1 = "";

    @SerializedName("multi_choice2")
    private String multi_choice2 = "";

    @SerializedName("multi_choice3")
    private String multi_choice3 = "";
    @SerializedName("multi_choice4")
    private String multi_choice4 = "";

    @SerializedName("multi_choice5")
    private String multi_choice5 = "";

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public void setSa_answer(String sa_answer) {
        this.sa_answer = sa_answer;
    }

    public void setMc_answer(int mc_answer) {
        this.mc_answer = mc_answer;
    }

    public void setLog_time(long log_time) {
        this.log_time = log_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public void setNum_answer(double num_answer) {
        this.num_answer = num_answer;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setmulti_choice1(String multi_choice1) {
        this.multi_choice1 = multi_choice1;
    }

    public void setmulti_choice2(String multi_choice2) {
        this.multi_choice2 = multi_choice2;
    }

    public void setmulti_choice3(String multi_choice3) {
        this.multi_choice3 = multi_choice3;
    }

    public void setmulti_choice4(String multi_choice4) {
        this.multi_choice4 = multi_choice4;
    }

    public void setmulti_choice5(String multi_choice5) {
        this.multi_choice5 = multi_choice5;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public String getSa_answer() {
        return sa_answer;
    }

    public int getMc_answer() {
        return mc_answer;
    }

    public long getLog_time() {
        return log_time;
    }

    public int getTotal_time() {
        return total_time;
    }

    public double getNum_answer() {
        return num_answer;
    }

    public String getCourse_id() {
        return course_id;
    }

    public int getNum() {
        return num;
    }

    public String getmulti_choice1() {
        return multi_choice1;
    }

    public String getmulti_choice2() {
        return multi_choice2;
    }

    public String getmulti_choice3() {
        return multi_choice3;
    }

    public String getmulti_choice4() { return multi_choice4; }

    public String getmulti_choice5() { return multi_choice5; }

    public String getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String action) {
        this.id = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Course keys

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
}