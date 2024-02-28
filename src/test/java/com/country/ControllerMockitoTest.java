package com.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.country.controller.CountryController;
import com.country.entity.Country;
import com.country.service.CountryServiceImpl;

@SpringBootTest(classes = ControllerMockitoTest.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerMockitoTest {

	@Mock
	CountryServiceImpl countryServiceImpl;

	@InjectMocks
	CountryController countryController;

	@Test
	@Order(1)
	public void testGetAllCountry() {
		when(countryServiceImpl.getAllCountry()).thenReturn(Stream.of(new Country(201,"France"),new Country(202,"Itly")).collect(Collectors.toList()));
		ResponseEntity<List<Country>> responseCountry = countryController.getAllCountry();
		List<Country> countryDemo = responseCountry.getBody();
		assertEquals(2, countryDemo.size());
	}

	@Test
	@Order(2)
	public void testGetCountry() {
		int id = 301;
		Country country = new Country(301, "Britain");
		when(countryServiceImpl.getCountry(id)).thenReturn(country);

		ResponseEntity<Country> responseCountry = countryController.getCountry(id);
		Country countryDemo = responseCountry.getBody();
		assertEquals(id, countryDemo.getId());
	}

	@Test
	@Order(3)
	public void testUpdateCountry() {
		Country country = new Country(1, "Germany");
		when(countryServiceImpl.upsert(country)).thenReturn(country);
		ResponseEntity<Country> responseCountry = countryController.updateCountry(country);
		Country countryDemo = responseCountry.getBody();
		assertEquals(country, countryDemo);
	}

	@Test
	@Order(4)
	public void testAddCountry() {
		Country country = new Country(401, "Brazil");
		when(countryServiceImpl.upsert(country)).thenReturn(country);
		ResponseEntity<Country> responseCountry = countryController.addCountry(country);
		Country countryDemo = responseCountry.getBody();
		assertEquals(country, countryDemo);
	}

	@Test
	@Order(5)
	public void testDeleteCountry() {
		int id = 501;
		String successMsg = "Delete Success";
		when(countryServiceImpl.delete(id)).thenReturn(successMsg);
		ResponseEntity<String> responseCountry = countryController.deleteCountry(id);
		String countryDemo = responseCountry.getBody();
		assertEquals(successMsg, countryDemo);
	}

}
