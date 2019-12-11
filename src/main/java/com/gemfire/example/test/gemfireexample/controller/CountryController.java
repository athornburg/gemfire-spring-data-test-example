package com.gemfire.example.test.gemfireexample.controller;

import com.gemfire.example.test.gemfireexample.repo.Country;
import com.gemfire.example.test.gemfireexample.repo.CountryRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountryController {

    private CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/country/{name}")
    public CountryData getCountry(@PathVariable String name) {
        Country countryFromGemfire = countryRepository.findById(name).get();
        return new CountryData(countryFromGemfire.getName(), countryFromGemfire.getCapital());
    }

    @PostMapping("/country")
    public void createCountry(@RequestBody CountryData country) {

        countryRepository.save(new Country(country.getName(), country.getCapital()));
    }
}
