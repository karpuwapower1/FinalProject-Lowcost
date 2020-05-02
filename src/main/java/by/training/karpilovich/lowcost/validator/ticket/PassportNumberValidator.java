package by.training.karpilovich.lowcost.validator.ticket;

import java.util.regex.Pattern;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PassportNumberValidator extends Validator {

	private static final String PASSPORT_NUMBER_REGEX = "([A-Z]{2}[0-9]{7})";

	private String passportNumber;

	public PassportNumberValidator(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public void validate() throws ValidatorException {
		isPasspotNumberPresent();
		ispasswordMatchPattern();
		continueValidate();
	}

	private void isPasspotNumberPresent() throws ValidatorException {
		if (passportNumber == null || passportNumber.isEmpty()) {
			throw new ValidatorException(MessageType.INVALID_PASSPORT_NUMBER.getMessage());
		}
	}

	private void ispasswordMatchPattern() throws ValidatorException {
		createMatcher();
		if (!matcher.find()) {
			throw new ValidatorException(MessageType.INVALID_PASSPORT_NUMBER.getMessage());
		}
	}

	private void createMatcher() {
		pattern = Pattern.compile(PASSPORT_NUMBER_REGEX);
		matcher = pattern.matcher(passportNumber);
	}
}