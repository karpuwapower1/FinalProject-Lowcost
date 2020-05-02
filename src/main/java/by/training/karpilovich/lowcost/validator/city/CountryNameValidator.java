package by.training.karpilovich.lowcost.validator.city;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class CountryNameValidator extends Validator {

	String name;

	public CountryNameValidator(String name) {
		this.name = name;
	}

	@Override
	public void validate() throws ValidatorException {
		if (name == null || name.isEmpty()) {
			throw new ValidatorException(MessageType.INVALID_COUNTRY_NAME.getMessage());
		}
		continueValidate();
	}

}
