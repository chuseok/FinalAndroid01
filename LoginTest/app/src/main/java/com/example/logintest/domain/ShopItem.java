package com.example.logintest.domain;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private int productId;
    private String image;
    private String name;
    private String category;
    private String description;
    private int price;
    private int count;

    public ShopItem(int productId, String image, String name, String category, String description, int price) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
