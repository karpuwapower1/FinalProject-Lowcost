package by.training.karpilovich.lowcost.validator.user;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class EmailValidatorTest {
	
	private static final String VALID_EMAIL = "123456qwerty@gmail.com";
	private static final String INVALID_EMAIL = "123asd.gmail.com";
	private static final String NULL_EMAIL = null;
	private static final String EMPTY_EMAIL = "";
	
	@Test
	public void validateTestValidEmail() throws ValidatorException {
		EmailValidator validator = new EmailValidator(VALID_EMAIL);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullEmail() throws ValidatorException {
		EmailValidator validator = new EmailValidator(NULL_EMAIL);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestEmptyEmail() throws ValidatorException {
		EmailValidator validator = new EmailValidator(EMPTY_EMAIL);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInEmail() throws ValidatorException {
		EmailValidator validator = new EmailValidator(INVALID_EMAIL);
		validator.validate();
	}
}