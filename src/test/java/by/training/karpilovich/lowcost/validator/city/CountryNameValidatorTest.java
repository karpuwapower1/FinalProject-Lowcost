package by.training.karpilovich.lowcost.validator.city;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class CountryNameValidatorTest {
	
	private static final String NORMAL_COUNTRY_NAME = "Belarus";
	private static final String NULL_COUNTRY_NAME = null;
	private static final String EMPTY_COUNTRY_NAME = "";
	
	@Test
	public void validateCountryNameNotNullAndNotEmpty() throws ValidatorException {
		CountryNameValidator validator = new CountryNameValidator(NORMAL_COUNTRY_NAME);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateCountryNameIsNull() throws ValidatorException {
		CountryNameValidator validator = new CountryNameValidator(NULL_COUNTRY_NAME);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateCountryNameIsEmpty() throws ValidatorException {
		CountryNameValidator validator = new CountryNameValidator(EMPTY_COUNTRY_NAME);
		validator.validate();
	}
}