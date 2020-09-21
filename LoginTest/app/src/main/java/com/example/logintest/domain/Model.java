package com.example.logintest.domain;

public class Model {
    private String title;
    private int desc;

    public Model(String title, int desc) {
        this.title = title;
        this.desc = desc;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
