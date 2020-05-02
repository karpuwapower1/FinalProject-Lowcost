package by.training.karpilovich.lowcost.validator.plane;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class PlaceQuantityValidatorTest {

	private static final int VALID_PLACE_QUANTITY = 100;
	private static final int INVALID_PLACE_QUANTITY = -10;

	@Test
	public void validateTestValidPlaceQuantity() throws ValidatorException {
		PlaceQuantityValidator validator = new PlaceQuantityValidator(VALID_PLACE_QUANTITY);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPlaceQuantity() throws ValidatorException {
		PlaceQuantityValidator validator = new PlaceQuantityValidator(INVALID_PLACE_QUANTITY);
		validator.validate();
	}
}