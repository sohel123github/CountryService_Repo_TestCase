package com.country.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.country.dao.CountryRepository;
import com.country.entity.Country;
import com.country.exception.ResourceNotFoundException;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Override
	public List<Country> getAllCountry() {
		List<Country> listOfCountry = countryRepository.findAll();
		System.out.println(listOfCountry);
		return listOfCountry;
	}

	@Override
	public Country getCountry(int id) {
		Country findById = countryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		System.out.println(findById);
		return findById;
	}

	@Override
	public Country upsert(Country country) {
		Country countryDemo = countryRepository.save(country);
		System.out.println(countryDemo);
		return countryDemo;
	}

	@Override
	public String delete(int id) {
		countryRepository.deleteById(id);
		System.out.println(id);
		return "deleted successfully..";
	}

}
