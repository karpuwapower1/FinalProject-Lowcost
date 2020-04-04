package by.training.karpilovich.lowcost.validator.flight;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class LuggageValidator extends Validator {

	private int luggage;

	public LuggageValidator(int luggage) {
		this.luggage = luggage;
	}

	@Override
	public void validate() throws ValidatorException {
		if (luggage < 0) {
			throw new ValidatorException(MessageType.INVALID_DEFAULT_LUGGAGE.getMessage());
		}
		continueValidate();
	}
}
