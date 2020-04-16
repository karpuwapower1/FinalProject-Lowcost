package by.training.karpilovich.lowcost.validator.coefficient;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PlaceCoefficientBoundToValidator extends Validator {

	private int boundTo;
	private int boundFrom;
	private int maxValue;

	public PlaceCoefficientBoundToValidator(int boundTo, int boundFrom, int maxValue) {
		this.boundTo = boundTo;
		this.boundFrom = boundFrom;
		this.maxValue = maxValue;
	}

	@Override
	public void validate() throws ValidatorException {
		if (boundTo <= boundFrom || boundTo > maxValue) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_TO.getMessage());
		}
		continueValidate();
	}
}