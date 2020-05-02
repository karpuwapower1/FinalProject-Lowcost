package by.training.karpilovich.lowcost.validator.flight;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class AvailablePlaceValidator extends Validator {

	private int places;

	public AvailablePlaceValidator(int places) {
		this.places = places;
	}

	@Override
	public void validate() throws ValidatorException {
		if (places <= 0) {
			throw new ValidatorException(MessageType.INSUFFICIENT_PLACE_QUANTITY.getMessage());
		}
		continueValidate();
	}
}