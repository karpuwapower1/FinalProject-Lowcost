package by.training.karpilovich.lowcost.validator.city;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class CityNameValidatorTest {
	
	private static final String NORMAL_CITY_NAME = "Minsk";
	private static final String NULL_CITY_NAME = null;
	private static final String EMPTY_CITY_NAME = "";
	
	@Test
	public void validateCityNameNotNullAndNotEmpty() throws ValidatorException {
		CityNameValidator validator = new CityNameValidator(NORMAL_CITY_NAME);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateCityNameIsNull() throws ValidatorException {
		CityNameValidator validator = new CityNameValidator(NULL_CITY_NAME);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateCityNameIsEmpty() throws ValidatorException {
		CityNameValidator validator = new CityNameValidator(EMPTY_CITY_NAME);
		validator.validate();
	}
}