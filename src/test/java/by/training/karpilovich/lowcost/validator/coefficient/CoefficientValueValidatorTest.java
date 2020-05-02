package by.training.karpilovich.lowcost.validator.coefficient;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class CoefficientValueValidatorTest {

	private static final BigDecimal VALID_VALUE = new BigDecimal("2.25");
	private static final BigDecimal INVALID_VALUE = new BigDecimal("-2.25");
	private static final BigDecimal NULL_VALUE = null;

	@Test
	public void validateTestValidValue() throws ValidatorException {
		CoefficientValueValidator validator = new CoefficientValueValidator(VALID_VALUE);
		validator.validate();

	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidValue() throws ValidatorException {
		CoefficientValueValidator validator = new CoefficientValueValidator(INVALID_VALUE);
		validator.validate();

	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullValue() throws ValidatorException {
		CoefficientValueValidator validator = new CoefficientValueValidator(NULL_VALUE);
		validator.validate();

	}
}