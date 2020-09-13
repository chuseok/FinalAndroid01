package com.example.logintest.domain;

public class Dragon {
    private String imagePath;
    private int progress;

    public Dragon(String imagePath, int progress) {
        this.imagePath = imagePath;
        this.progress = progress;

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
