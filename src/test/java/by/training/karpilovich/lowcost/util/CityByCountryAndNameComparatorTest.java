package by.training.karpilovich.lowcost.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.entity.City;


public class CityByCountryAndNameComparatorTest {
	
	private City city;
	
	@Before
	public void initializeCity() {
		city = buildCity("Minsk", "Belarus");
	}
	
	@Test
	public void compareTestEqualsCity() {
		City compared = buildCity("Minsk", "Belarus");
		CityByCountryAndNameComparator comparator = new CityByCountryAndNameComparator();
		int expected = 0;
		int actual = comparator.compare(city, compared);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void compareTestCityLess() {
		City compared = buildCity("Brussels", "Belgium");
		CityByCountryAndNameComparator comparator = new CityByCountryAndNameComparator();
		int actual = comparator.compare(city, compared);
		Assert.assertTrue(actual < 0);
	}
	
	@Test
	public void compareTestCityGreater() {
		City compared = buildCity("Grodno", "Belarus");
		CityByCountryAndNameComparator comparator = new CityByCountryAndNameComparator();
		int actual = comparator.compare(city, compared);
		Assert.assertTrue(actual > 0);
	}
	
	
	private City buildCity(String cityName, String countryName) {
		CityBuilder builder = new CityBuilder();
		builder.setCityName(cityName);
		builder.setCityCountry(countryName);
		return builder.getCity();
	}
}