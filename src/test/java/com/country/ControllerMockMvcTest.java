package com.country;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.country.controller.CountryController;
import com.country.entity.Country;
import com.country.service.CountryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = "com.country")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMvcTest.class })
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryServiceImpl countryServiceImpl;

	@InjectMocks
	CountryController countryController;

	List<Country> myCountrys;

	Country country;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test
	@Order(1)
	public void testGetAllCountry() throws Exception {
		List<Country> myCountrys = new ArrayList<>();
		myCountrys.add(new Country(1, "USA"));
		myCountrys.add(new Country(2, "UK"));

		when(countryServiceImpl.getAllCountry()).thenReturn(myCountrys);

		this.mockMvc.perform(get("/country")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	@Order(2)
	public void testGetCountry() throws Exception {
		int id = 101;
		country = new Country(101, "France");

		when(countryServiceImpl.getCountry(id)).thenReturn(country);

		this.mockMvc.perform(get("/country/{id}", id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(101))
				.andExpect(jsonPath("$.name").value("France")).andDo(print());
	}

	@Test
	@Order(3)
	public void testUpdateCountry() throws Exception {
		country = new Country(1, "INDIA");

		when(countryServiceImpl.upsert(country)).thenReturn(country);

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);

		this.mockMvc.perform(put("/country").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	@Order(4)
	public void testAddCountry() throws Exception {
		country = new Country(501, "Japan");

		when(countryServiceImpl.upsert(country)).thenReturn(country);

		ObjectMapper mapper = new ObjectMapper(); // We here use (ObjectMapper) to convert [java object] into [Json format]..
		String jsonBody = mapper.writeValueAsString(country);

		this.mockMvc.perform(post("/country").content(jsonBody).contentType(MediaType.APPLICATION_JSON)) //perform method..
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@Order(5)
	public void testDeleteCountry() throws Exception {
		int id = 1;
		country = new Country(1, "China");
		when(countryServiceImpl.getCountry(id)).thenReturn(country);

		this.mockMvc.perform(delete("/country/{id}", id)).andExpect(status().isOk());

	}

}
