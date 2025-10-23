package com.example.myapplication;

public class PinItem {
    private String title;
    private String description;
    private int imageRes;

    public PinItem(String title, String description, int imageRes) {
        this.title = title;
        this.description = description;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
