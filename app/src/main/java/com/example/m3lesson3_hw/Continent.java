package com.example.m3lesson3_hw;

import java.util.ArrayList;
import java.util.List;

public class Continent extends Location{
    private List<Country> countries;

    public Continent(String name, String imageUrl, List<Country> countries) {
        super(name, imageUrl);
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
