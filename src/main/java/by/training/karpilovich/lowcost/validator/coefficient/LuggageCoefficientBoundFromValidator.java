package by.training.karpilovich.lowcost.validator.coefficient;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class LuggageCoefficientBoundFromValidator extends Validator {

	private int boundFrom;
	private int permittedWeight;

	public LuggageCoefficientBoundFromValidator(int bound, int permittedWeigth) {
		this.boundFrom = bound;
		this.permittedWeight = permittedWeigth;
	}

	@Override
	public void validate() throws ValidatorException {
		if (boundFrom < 0 || boundFrom < permittedWeight) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_FROM.getMessage());
		}
		continueValidate();
	}
}