package by.training.karpilovich.lowcost.validator.coefficient;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PlaceCoefficientBoundFromValidator extends Validator {

	private int boundFrom;
	private int maxBound;

	public PlaceCoefficientBoundFromValidator(int boundFrom, int maxBound) {
		this.boundFrom = boundFrom;
		this.maxBound = maxBound;
	}

	@Override
	public void validate() throws ValidatorException {
		if (boundFrom < 0 || boundFrom >= maxBound) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_FROM.getMessage());
		}
		continueValidate();
	}
}