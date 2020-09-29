package com.example.logintest.domain;

import java.io.Serializable;

public class Dragon implements Serializable {
    private String imagePath;
    private int progress;
    private int dragonId;

    public Dragon(String imagePath, int progress, int dragonId) {
        this.imagePath = imagePath;
        this.progress = progress;
        this.dragonId = dragonId;
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

    public int getDragonId() {
        return dragonId;
    }

    public void setDragonId(int dragonId) {
        this.dragonId = dragonId;
    }
}
