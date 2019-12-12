package com.gemfire.example.test.gemfireexample.controller;

import com.gemfire.example.test.gemfireexample.repo.Country;
import com.gemfire.example.test.gemfireexample.repo.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/country")
    public List<CountryData> getCountries() {
        return countryRepository.findAllWithQuery()
                .stream()
                .map(data -> new CountryData(data.getName(), data.getCapital())).collect(Collectors.toList());
    }

    @GetMapping("/country/byQuery")
    public List<CountryData> workingGetCountries() {
        Iterator<Country> countriesIterable = countryRepository.findAll().iterator();
        List<Country> target = new ArrayList<>();
        countriesIterable.forEachRemaining(target::add);
        return target
                .stream()
                .map(data -> new CountryData(data.getName(), data.getCapital())).collect(Collectors.toList());
    }
}
