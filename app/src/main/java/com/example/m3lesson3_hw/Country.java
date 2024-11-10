package com.example.m3lesson3_hw;

import java.util.ArrayList;
import java.util.List;

public class Country extends Location{
    private List<City> cities;

    public Country(String name, String imageUrl, ArrayList<City> cities) {
        super(name, imageUrl);
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
