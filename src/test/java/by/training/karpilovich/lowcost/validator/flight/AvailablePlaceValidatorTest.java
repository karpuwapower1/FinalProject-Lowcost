package by.training.karpilovich.lowcost.validator.flight;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class AvailablePlaceValidatorTest {

	private static final int VALID_PLACE = 100;
	private static final int INVALID_PLACE = 0;

	@Test
	public void validateTestValidPlace() throws ValidatorException {
		AvailablePlaceValidator validator = new AvailablePlaceValidator(VALID_PLACE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPlace() throws ValidatorException {
		AvailablePlaceValidator validator = new AvailablePlaceValidator(INVALID_PLACE);
		validator.validate();
	}
}