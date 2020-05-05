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