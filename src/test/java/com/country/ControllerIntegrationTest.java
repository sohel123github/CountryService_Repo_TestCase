package com.country;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.country.entity.Country;

@SpringBootTest(classes = { ControllerIntegrationTest.class })
@TestMethodOrder(OrderAnnotation.class)
public class ControllerIntegrationTest {

	@Test
	@Order(1)
	public void getAllCountriesIntegrationTest() throws JSONException {
		String expected = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"name\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"name\": \"Washington\"\r\n"
				+ "    }\r\n"
				+ "]";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:2020/country", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(2)
	public void getCountryIntegrationTest() throws JSONException {
		String expected = "{\r\n" + "    \"id\": 1,\r\n" + "    \"name\": \"Delhi\"\r\n" + "}";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:2020/country/1", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@Order(3)
	public void addCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "France");

		String expected = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"name\": \"France\"\r\n"
				+ "}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> result = new HttpEntity<>(country, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:2020/country", result,
				String.class);
		System.out.println(response.getBody());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(4)
	public void updateCountryIntegrationTest() throws JSONException {
		Country country = new Country(3, "UK");

		String expected = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"name\": \"UK\"\r\n"
				+ "}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<>(country, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:2020/country", HttpMethod.PUT,
				request, String.class);
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

//	@Test
//	@Order(5)
//	public void deleteCountryIntegrationTest() throws JSONException {
//		Country country = new Country(3, "UK");
//
//		String expected = "{\r\n" + "    \"id\": 3,\r\n" + "    \"name\": \"UK\"\r\n" + "}";
//
//		TestRestTemplate restTemplate = new TestRestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Country> request = new HttpEntity<>(country, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange("http://localhost:2020/country/3", HttpMethod.DELETE,request, String.class);
//		System.out.println(response.getBody());
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		JSONAssert.assertEquals(expected, response.getBody(), false);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
