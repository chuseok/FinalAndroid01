package com.example.logintest.domain;

public class Inven {
    private int productId;
    private String imagePath;
    private int count;
    private int dragonId;

    public Inven(int productId, String imagePath, int count, int dragonId){
        this.imagePath = imagePath;
        this.count = count;
        this.productId = productId;
        this.dragonId = dragonId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDragonId() {
        return dragonId;
    }

    public void setDragonId(int dragonId) {
        this.dragonId = dragonId;
    }
}
