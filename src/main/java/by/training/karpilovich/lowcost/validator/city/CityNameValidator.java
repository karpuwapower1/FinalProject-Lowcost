package by.training.karpilovich.lowcost.validator.city;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class CityNameValidator extends Validator {

	private String name;

	public CityNameValidator(String name) {
		this.name = name;
	}

	@Override
	public void validate() throws ValidatorException {
		if (name == null || name.isEmpty()) {
			throw new ValidatorException(MessageType.INVALID_CITY_NAME.getMessage());
		}
		continueValidate();
	}

}
