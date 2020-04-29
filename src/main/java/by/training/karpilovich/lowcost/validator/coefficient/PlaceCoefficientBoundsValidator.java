package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PlaceCoefficientBoundsValidator extends Validator {
	
	private int boundFrom;
	private int boundTo;
	private SortedSet<PlaceCoefficient> coefficients;
	
	public PlaceCoefficientBoundsValidator(int boundFrom, int boundTo, SortedSet<PlaceCoefficient> coefficients) {
		this.boundFrom = boundFrom;
		this.boundTo = boundTo;
		this.coefficients = coefficients;
	}

	@Override
	public void validate() throws ValidatorException {
		for (PlaceCoefficient coefficient : coefficients) {
			if (boundFrom >= coefficient.getFrom() && boundFrom <= coefficient.getTo()) {
				throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_FROM.getMessage());
			}
			if (boundTo >= coefficient.getFrom() && boundTo <= coefficient.getTo()) {
				throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_TO.getMessage());
			}
		}
		continueValidate();
	}
}