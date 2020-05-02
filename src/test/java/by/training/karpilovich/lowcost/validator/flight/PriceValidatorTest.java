package by.training.karpilovich.lowcost.validator.flight;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PriceValidatorTest {

	private static final BigDecimal VALID_PRICE = new BigDecimal("10.0");
	private static final BigDecimal INVALID_PRICE = new BigDecimal("-10.0");
	private static final BigDecimal NULL_PRICE = null;

	@Test
	public void validateTestValidPrice() throws ValidatorException {
		PriceValidator validator = new PriceValidator(VALID_PRICE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPrice() throws ValidatorException {
		PriceValidator validator = new PriceValidator(INVALID_PRICE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullPrice() throws ValidatorException {
		PriceValidator validator = new PriceValidator(NULL_PRICE);
		validator.validate();
	}
}