package by.training.karpilovich.lowcost.validator.flight;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PassengerQuantityValidator extends Validator {
	
	private int quantity;
	
	public PassengerQuantityValidator(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public void validate() throws ValidatorException {
		if (quantity <= 0) {
			throw new ValidatorException(MessageType.INVALID_PASSEGER_QUANTITY.getMessage());
		}
		continueValidate();
	}

}
