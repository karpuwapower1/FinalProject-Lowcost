package by.training.karpilovich.lowcost.validator.ticket;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PassportNumberValidatorTest {
	
	private static final String VALID_PASSPORT_NUMBER = "MP1234567";
	private static final String INVALID_PASSPORT_NUMBER = "M01234567";
	private static final String NULL_PASSPORT_NUMBER = null;
	private static final String EMPTY_PASSPORT_NUMBER = "";
	
	@Test
	public void validateTestValidPassportNumber() throws ValidatorException {
		PassportNumberValidator validator = new PassportNumberValidator(VALID_PASSPORT_NUMBER);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullPassportNumber() throws ValidatorException {
		PassportNumberValidator validator = new PassportNumberValidator(NULL_PASSPORT_NUMBER);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestEmptyPassportNumber() throws ValidatorException {
		PassportNumberValidator validator = new PassportNumberValidator(EMPTY_PASSPORT_NUMBER);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInPassportNumber() throws ValidatorException {
		PassportNumberValidator validator = new PassportNumberValidator(INVALID_PASSPORT_NUMBER);
		validator.validate();
	}
}