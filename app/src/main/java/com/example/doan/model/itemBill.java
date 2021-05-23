package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class itemBill {
    @SerializedName("id_product")
    private String id_product;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private int price;

    public itemBill(String id_product, int quantity, int price) {
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}