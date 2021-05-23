package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Product {
    @SerializedName("_id")
    private String _id;
    @SerializedName("id_brand")
    private String id_brand;
    @SerializedName("id_category")
    private String id_category;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("product")
    private String product;
    @SerializedName("stock")
    private int stock;
    @SerializedName("price")
    private int price;
    @SerializedName("img")
    private String img;
    @SerializedName("message")
    private String message;

    public Product(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product(String _id, String id_brand, String id_category, String id_user, String product, int stock, int price, File image) {
        this._id = _id;
        this.id_brand = id_brand;
        this.id_category = id_category;
        this.id_user = id_user;
        this.product = product;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    private File image;

    public Product(String _id, String id_brand, String id_category, String id_user, String product, int stock, int price, String img) {
        this._id = _id;
        this.id_brand = id_brand;
        this.id_category = id_category;
        this.id_user = id_user;
        this.product = product;
        this.stock = stock;
        this.price = price;
        this.img = img;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_brand() {
        return id_brand;
    }

    public void setId_brand(String id_brand) {
        this.id_brand = id_brand;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}