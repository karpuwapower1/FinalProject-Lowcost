package by.training.karpilovich.lowcost.validator.plane;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class ModelValidator extends Validator {

	private String model;

	public ModelValidator(String model) {
		this.model = model;
	}

	@Override
	public void validate() throws ValidatorException {
		if (model == null || model.isEmpty()) {
			throw new ValidatorException(MessageType.INVALID_PLANE_MODEL.getMessage());
		}
		continueValidate();
	}
}