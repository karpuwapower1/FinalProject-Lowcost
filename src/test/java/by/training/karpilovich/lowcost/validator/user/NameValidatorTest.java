package by.training.karpilovich.lowcost.validator.user;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class NameValidatorTest {
	
	private static final String VALID_NAME = "MP1234567";
	private static final String NULL_NAME = null;
	private static final String EMPTY_NAME = "";
	
	@Test
	public void validateTestValidName() throws ValidatorException {
		NameValidator validator = new NameValidator(VALID_NAME);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullName() throws ValidatorException {
		NameValidator validator = new NameValidator(NULL_NAME);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestEmptyName() throws ValidatorException {
		NameValidator validator = new NameValidator(EMPTY_NAME);
		validator.validate();
	}
}