package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Bill {
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("bill")
    private String bill;
    @SerializedName("time")
    private String time;
    @SerializedName("items")
    private ArrayList<itemBill> items;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bill(String message) {
        this.message = message;
    }

    public Bill(String _id, String id_user, String bill, String time, ArrayList<itemBill> items) {
        this._id = _id;
        this.id_user = id_user;
        this.bill = bill;
        this.time = time;
        this.items = items;
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

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<itemBill> getItems() {
        return items;
    }

    public void setItems(ArrayList<itemBill> items) {
        this.items = items;
    }
}