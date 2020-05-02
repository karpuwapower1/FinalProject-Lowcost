package by.training.karpilovich.lowcost.validator.flight;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class NumberValidatorTest {

	private static final String VALID_NUMBER = "123ABC";
	private static final String NULL_NUMBER = null;
	private static final String EMPTY_NUMBER = "";

	@Test
	public void validateTestValidNumber() throws ValidatorException {
		NumberValidator validator = new NumberValidator(VALID_NUMBER);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullNumber() throws ValidatorException {
		NumberValidator validator = new NumberValidator(NULL_NUMBER);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestEmptyNumber() throws ValidatorException {
		NumberValidator validator = new NumberValidator(EMPTY_NUMBER);
		validator.validate();
	}
}