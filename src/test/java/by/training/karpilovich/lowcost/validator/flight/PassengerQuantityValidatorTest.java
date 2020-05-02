package by.training.karpilovich.lowcost.validator.flight;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PassengerQuantityValidatorTest {
	
	private static final int VALID_PASSENGER_QUANTITY = 100;
	private static final int INVALID_PASSENGER_QUANTITY = -10;

	@Test
	public void validateTestValidPassengerQuantity() throws ValidatorException {
		PassengerQuantityValidator validator = new PassengerQuantityValidator(VALID_PASSENGER_QUANTITY);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPassengerQuantity() throws ValidatorException {
		PassengerQuantityValidator validator = new PassengerQuantityValidator(INVALID_PASSENGER_QUANTITY);
		validator.validate();
	}
}