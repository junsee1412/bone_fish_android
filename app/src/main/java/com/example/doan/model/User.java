package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String _id;
    @SerializedName("email")
    private String email;
    @SerializedName("pass")
    private String pass;
    @SerializedName("token")
    private String token;
    @SerializedName("message")
    private String message;

    public User(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User(String _id, String email, String pass) {
        this._id = _id;
        this.email = email;
        this.pass = pass;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}