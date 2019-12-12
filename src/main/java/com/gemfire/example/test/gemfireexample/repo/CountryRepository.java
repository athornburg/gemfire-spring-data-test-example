package com.gemfire.example.test.gemfireexample.repo;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends GemfireRepository<Country, String> {

    @Override
    Optional<Country> findById(String id);

    @Query("select * from /country")
    List<Country> findAllWithQuery();


}
