package com.example.m3lesson3_hw;

import java.util.ArrayList;

public class City extends Location{
    private String description;

    public City(String name, String imageUrl, String description) {
        super(name, imageUrl);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
