package com.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.country.dao.CountryRepository;
import com.country.entity.Country;
import com.country.service.CountryServiceImpl;

@SpringBootTest(classes = ServiceMockitoTest.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceMockitoTest {

	@Mock
	CountryRepository countryRepository;

	@InjectMocks
	CountryServiceImpl countryServiceImpl;

	@Test
	@Order(1)
	public void testGetAllCountry() {
		when(countryRepository.findAll()).thenReturn(Stream.of(new Country(1,"Peru"), new Country(2,"Dubai")).collect(Collectors.toList()));
		assertEquals(2,countryServiceImpl.getAllCountry().size());
	}

	@Test
	@Order(2)
	public void testGetCountry() {
		int id = 1;
		Country countryDemo = new Country(1, "UK");
		when(countryRepository.findById(id)).thenReturn(Optional.of(countryDemo));
		assertEquals(id, countryServiceImpl.getCountry(id).getId());
	}

	@Test
	@Order(3)
	public void testUpsert() {
		Country countryDemo = new Country(201, "Poland");
		when(countryRepository.save(countryDemo)).thenReturn(countryDemo);
		assertEquals(countryDemo, countryServiceImpl.upsert(countryDemo));
	}

	@Test
	@Order(4)
	public void testDelete() {
		int id = 1;
		doNothing().when(countryRepository).deleteById(id);
		countryServiceImpl.delete(id);
		verify(countryRepository, times(1)).deleteById(id);
	}

}
