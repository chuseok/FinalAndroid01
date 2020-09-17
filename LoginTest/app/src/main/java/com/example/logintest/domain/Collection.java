package com.example.logintest.domain;

public class Collection {
    private String image;
    private String name;
    private boolean possession;

    public Collection(String image, String name, boolean possession) {
        this.image = image;
        this.name = name;
        this.possession = possession;
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

    public boolean isPossession() {
        return possession;
    }

    public void setPossession(boolean possession) {
        this.possession = possession;
    }
}
