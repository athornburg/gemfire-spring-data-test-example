package com.gemfire.example.test.gemfireexample.repo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

@Region(value = "country")
public class Country implements Serializable {

    @Id
    private final String name;

    private final String capital;

    @PersistenceConstructor
    public Country(String name, String age) {
        this.name = name;
        this.capital = age;
    }

    @Override
    public String toString() {
        return String.format("%s is %d years old", getName(), getCapital());
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }
}
