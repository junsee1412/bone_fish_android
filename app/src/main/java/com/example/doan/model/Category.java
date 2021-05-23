package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("category")
    private String category;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Category(String message) {
        this.message = message;
    }

    public Category(String _id, String id_user, String category) {
        this._id = _id;
        this.id_user = id_user;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}