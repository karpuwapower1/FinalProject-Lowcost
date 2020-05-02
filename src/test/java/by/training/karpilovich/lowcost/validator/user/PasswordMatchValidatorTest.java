package by.training.karpilovich.lowcost.validator.user;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PasswordMatchValidatorTest {
	
	private static final String PASSWORD = "1234qwerty";
	private static final String VALID_REPEAT_PASSWORD = "1234qwerty";
	private static final String INVALID_REPEAT_PASSWORD = "56789uiop";
	
	@Test
	public void validateTestPasswordMatch() throws ValidatorException {
		PasswordMatchValidator validator = new PasswordMatchValidator(PASSWORD, VALID_REPEAT_PASSWORD);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestPasswordNotMatch() throws ValidatorException {
		PasswordMatchValidator validator = new PasswordMatchValidator(PASSWORD, INVALID_REPEAT_PASSWORD);
		validator.validate();
	}
}