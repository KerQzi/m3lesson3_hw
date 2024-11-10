package com.example.m3lesson3_hw;

public class Location {
    private String name;
    private String imageUrl;

    public Location(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
