package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class Brand {
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("brand")
    private String brand;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Brand(String message) {
        this.message = message;
    }

    public Brand(String _id, String id_user, String brand) {
        this._id = _id;
        this.id_user = id_user;
        this.brand = brand;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}