package by.training.karpilovich.lowcost.validator.user;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class AmountValidatorTest {
	
	private static final BigDecimal VALID_AMOUNT = new BigDecimal("10.0");
	private static final BigDecimal INVALID_AMOUNT = new BigDecimal("-10.0");
	private static final BigDecimal NULL_AMOUNT = null;

	@Test
	public void validateTestValidAmount() throws ValidatorException {
		AmountValidator validator = new AmountValidator(VALID_AMOUNT);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidAmount() throws ValidatorException {
		AmountValidator validator = new AmountValidator(INVALID_AMOUNT);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullAmount() throws ValidatorException {
		AmountValidator validator = new AmountValidator(NULL_AMOUNT);
		validator.validate();
	}
}