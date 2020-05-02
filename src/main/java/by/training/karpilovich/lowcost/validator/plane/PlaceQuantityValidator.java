package by.training.karpilovich.lowcost.validator.plane;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PlaceQuantityValidator extends Validator {

	private int quantity;

	public PlaceQuantityValidator(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public void validate() throws ValidatorException {
		if (quantity <= 0) {
			throw new ValidatorException(MessageType.INVALID_PLANE_PASSENGER_QUANTITY.getMessage());
		}
		continueValidate();
	}

}
