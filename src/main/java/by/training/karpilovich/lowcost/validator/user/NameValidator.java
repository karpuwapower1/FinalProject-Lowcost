package by.training.karpilovich.lowcost.validator.user;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class NameValidator extends Validator {

	private String name;

	public NameValidator(String name) {
		this.name = name;
	}

	@Override
	public void validate() throws ValidatorException {
		if (name == null || name.isEmpty()) {
			throw new ValidatorException(MessageType.ILLEGAL_NAME.getMessage());
		}
		continueValidate();
	}
}