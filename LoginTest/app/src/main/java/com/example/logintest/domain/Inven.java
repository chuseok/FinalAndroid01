package com.example.logintest.domain;

public class Inven {
    private String imagePath;
    private int count;

    public Inven(String imagePath, int count){
        this.imagePath = imagePath;
        this.count = count;
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
}
