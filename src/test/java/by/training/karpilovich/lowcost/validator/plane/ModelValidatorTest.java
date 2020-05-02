package by.training.karpilovich.lowcost.validator.plane;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class ModelValidatorTest {

	private static final String VALID_MODEL = "123ABC";
	private static final String NULL_MODEL = null;
	private static final String EMPTY_MODEL = "";

	@Test
	public void validateTestValidModel() throws ValidatorException {
		ModelValidator validator = new ModelValidator(VALID_MODEL);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullModel() throws ValidatorException {
		ModelValidator validator = new ModelValidator(NULL_MODEL);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestEmptyModel() throws ValidatorException {
		ModelValidator validator = new ModelValidator(EMPTY_MODEL);
		validator.validate();
	}
}