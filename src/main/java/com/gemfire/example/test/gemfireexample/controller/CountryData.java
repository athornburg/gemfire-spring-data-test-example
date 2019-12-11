package com.gemfire.example.test.gemfireexample.controller;

public class CountryData {

    private String name;
    private String capital;

    public CountryData() {

    }

    public CountryData(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }


    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }
}
