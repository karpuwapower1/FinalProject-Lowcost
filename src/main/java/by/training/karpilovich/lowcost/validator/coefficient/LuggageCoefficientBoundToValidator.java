package by.training.karpilovich.lowcost.validator.coefficient;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class LuggageCoefficientBoundToValidator extends Validator {

	private int boundFrom;
	private int boundTo;

	public LuggageCoefficientBoundToValidator(int boundFrom, int boundTo) {
		this.boundFrom = boundFrom;
		this.boundTo = boundTo;
	}

	@Override
	public void validate() throws ValidatorException {
		if (boundTo <= boundFrom) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_TO.getMessage());
		}
		continueValidate();
	}
}