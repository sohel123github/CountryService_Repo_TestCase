package com.country.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.country.entity.Country;
import com.country.service.CountryServiceImpl;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	CountryServiceImpl countryServiceImpl;

	@GetMapping
	public ResponseEntity<List<Country>> getAllCountry() {
		List<Country> listOfCountry = countryServiceImpl.getAllCountry();
		return new ResponseEntity<>(listOfCountry, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Country> getCountry(@PathVariable int id) {
		Country country = countryServiceImpl.getCountry(id);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
		Country countryDemo = countryServiceImpl.upsert(country);
		return new ResponseEntity<>(countryDemo, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		Country countryDemo = countryServiceImpl.upsert(country);
		return new ResponseEntity<>(countryDemo, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCountry(@PathVariable int id) {
		String status = countryServiceImpl.delete(id);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
