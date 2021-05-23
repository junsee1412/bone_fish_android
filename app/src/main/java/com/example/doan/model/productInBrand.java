package com.example.doan.model;

import java.util.List;

public class productInBrand {
    String id;
    String brand;
    List<Product> productList;

    public productInBrand(String id, String brand, List<Product> productList) {
        this.id = id;
        this.brand = brand;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
