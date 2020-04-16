package by.training.karpilovich.lowcost.validator.flight;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class NumberValidator extends Validator {

	private String number;

	public NumberValidator(String number) {
		this.number = number;
	}

	@Override
	public void validate() throws ValidatorException {
		if (number == null || number.isEmpty()) {
			throw new ValidatorException(MessageType.INVALID_FLIGHT_NUMBER.getMessage());
		}
		continueValidate();
	}
}