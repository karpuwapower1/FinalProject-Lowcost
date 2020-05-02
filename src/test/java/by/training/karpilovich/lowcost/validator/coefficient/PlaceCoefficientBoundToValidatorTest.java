package by.training.karpilovich.lowcost.validator.coefficient;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PlaceCoefficientBoundToValidatorTest {

	private static final int BOUND_FROM = 10;
	private static final int MAX_VALUE = 50;
	private static final int VALID_BOUND_TO = 49;
	private static final int BOUND_TO_LESS_THAN_FROM = 9;
	private static final int BOUND_TO_GREATER_THAN_MAX_VALUE = 59;

	@Test
	public void validateTestValidBoundTo() throws ValidatorException {
		PlaceCoefficientBoundToValidator validator = new PlaceCoefficientBoundToValidator(VALID_BOUND_TO, BOUND_FROM,
				MAX_VALUE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundToLessThanFrom() throws ValidatorException {
		PlaceCoefficientBoundToValidator validator = new PlaceCoefficientBoundToValidator(BOUND_TO_LESS_THAN_FROM,
				BOUND_FROM, MAX_VALUE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundToGreaterThanMaxValue() throws ValidatorException {
		PlaceCoefficientBoundToValidator validator = new PlaceCoefficientBoundToValidator(
				BOUND_TO_GREATER_THAN_MAX_VALUE, BOUND_FROM, MAX_VALUE);
		validator.validate();
	}
}