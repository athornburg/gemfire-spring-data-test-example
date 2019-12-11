package com.gemfire.example.test.gemfireexample.repo;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends GemfireRepository<Country, String> {
}
