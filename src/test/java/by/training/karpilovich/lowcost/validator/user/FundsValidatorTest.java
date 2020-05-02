package by.training.karpilovich.lowcost.validator.user;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class FundsValidatorTest {
	
	private static final BigDecimal SUMBSTRACT = new BigDecimal("10.0");
	private static final BigDecimal VALID_BALANCE = new BigDecimal("20.0");
	private static final BigDecimal INVALID_BALANCE = new BigDecimal("5.0");
	
	@Test
	public void validateTestAmountGreaterThanSubstract() throws ValidatorException {
		FundsValidator validator = new FundsValidator(VALID_BALANCE, SUMBSTRACT);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestAmountLessThanSubstract() throws ValidatorException {
		FundsValidator validator = new FundsValidator(INVALID_BALANCE, SUMBSTRACT);
		validator.validate();
	}
}