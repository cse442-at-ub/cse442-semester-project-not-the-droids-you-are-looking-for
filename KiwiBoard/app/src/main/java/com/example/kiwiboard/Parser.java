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

    @SerializedName("status")
    private String status = "";

    @SerializedName("success")
    private String success = "";

    @SerializedName("failed")
    private String failed = "";

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
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
}