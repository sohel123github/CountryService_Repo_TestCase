package com.country.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.country.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
