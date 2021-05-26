package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class itemBill {
    @SerializedName("id_product")
    private String id_product;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("product")
    private String product;
    private String image;

    public itemBill(String id_product, String quantity, String price, String product, String image) {
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.image = image;
    }

    public itemBill(String id_product, String quantity, String price, String product) {
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() { return product; }

    public void setProduct(String product) { this.product = product; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}