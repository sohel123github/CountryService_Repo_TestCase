package com.country.service;

import java.util.List;
import com.country.entity.Country;

public interface CountryService {

	public List<Country> getAllCountry();

	public Country getCountry(int id);

	public Country upsert(Country country);

	public String delete(int id);

}
