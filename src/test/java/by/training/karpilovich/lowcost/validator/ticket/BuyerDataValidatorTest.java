package by.training.karpilovich.lowcost.validator.ticket;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class BuyerDataValidatorTest {

	private static final String VALID_EMAIL = "alala@gmail.com";
	private static final String INVALID_EMAIL = "alala.gmail.com";
	private static final String VALID_FIRST_NAME = "Alisksei";
	private static final String INVALID_FIRST_NAME = "";
	private static final String VALID_LAST_NAME = "Karpilovich";
	private static final String INVALID_LAST_NAME = "";
	private static final String VALID_PASSPORT_NUMBER = "MP1234567";
	private static final String INVALID_PASSPORT_NUMBER = "MP123456";
	private static final int VALID_LUGGAGE_QUANTITY = 10;
	private static final int INVALID_LUGGAGE_QUANTITY = -10;

	@Test
	public void validateTestValidBuyerDate() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME,
				VALID_PASSPORT_NUMBER, VALID_LUGGAGE_QUANTITY);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidEmail() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(INVALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME,
				VALID_PASSPORT_NUMBER, VALID_LUGGAGE_QUANTITY);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestInvalidFirstName() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(VALID_EMAIL, INVALID_FIRST_NAME, VALID_LAST_NAME,
				VALID_PASSPORT_NUMBER, VALID_LUGGAGE_QUANTITY);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestInvalidLastName() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(VALID_EMAIL, VALID_FIRST_NAME, INVALID_LAST_NAME,
				VALID_PASSPORT_NUMBER, VALID_LUGGAGE_QUANTITY);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestInvalidPassportNumber() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME,
				INVALID_PASSPORT_NUMBER, VALID_LUGGAGE_QUANTITY);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestInvalidLuggageQuantity() throws ValidatorException {
		BuyerDataValidator validator = new BuyerDataValidator(VALID_EMAIL, VALID_FIRST_NAME, VALID_LAST_NAME,
				VALID_PASSPORT_NUMBER, INVALID_LUGGAGE_QUANTITY);
		validator.validate();
	}
}
