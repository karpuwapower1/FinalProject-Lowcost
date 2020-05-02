package by.training.karpilovich.lowcost.validator.coefficient;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PlaceCoefficientBoundFromValidatorTest {

	private static final int MAX_BOUND = 139;
	private static final int VALID_BOUND_FROM = 100;
	private static final int BOUND_FROM_LESS_THAN_ZERO = -100;
	private static final int BOUND_FROM_GREATER_THAN_MAX_BOUND = 150;

	@Test
	public void validateTestValidBoundFrom() throws ValidatorException {
		PlaceCoefficientBoundFromValidator validator = new PlaceCoefficientBoundFromValidator(VALID_BOUND_FROM,
				MAX_BOUND);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundFromLessThanZero() throws ValidatorException {
		PlaceCoefficientBoundFromValidator validator = new PlaceCoefficientBoundFromValidator(BOUND_FROM_LESS_THAN_ZERO,
				MAX_BOUND);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundFromGreaterThanMaxBound() throws ValidatorException {
		PlaceCoefficientBoundFromValidator validator = new PlaceCoefficientBoundFromValidator(
				BOUND_FROM_GREATER_THAN_MAX_BOUND, MAX_BOUND);
		validator.validate();
	}
}