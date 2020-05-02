package by.training.karpilovich.lowcost.validator.ticket;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class LuggagePriceValidatorTest {
	
	private static final BigDecimal VALID_PRICE = new BigDecimal("10.0");
	private static final BigDecimal INVALID_PRICE = new BigDecimal("-10.0");
	private static final BigDecimal NULL_PRICE = null;

	@Test
	public void validateTestValidPrice() throws ValidatorException {
		LuggagePriceValidator validator = new LuggagePriceValidator(VALID_PRICE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPrice() throws ValidatorException {
		LuggagePriceValidator validator = new LuggagePriceValidator(INVALID_PRICE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullPrice() throws ValidatorException {
		LuggagePriceValidator validator = new LuggagePriceValidator(NULL_PRICE);
		validator.validate();
	}
}