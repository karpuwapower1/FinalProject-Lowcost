package by.training.karpilovich.lowcost.validator.flight;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class LuggageValidatorTest {
	
	private static final int VALID_LUGGAGE = 100;
	private static final int INVALID_LUGGAGE = -10;

	@Test
	public void validateTestValidLuggage() throws ValidatorException {
		LuggageValidator validator = new LuggageValidator(VALID_LUGGAGE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidLuggage() throws ValidatorException {
		LuggageValidator validator = new LuggageValidator(INVALID_LUGGAGE);
		validator.validate();
	}
}