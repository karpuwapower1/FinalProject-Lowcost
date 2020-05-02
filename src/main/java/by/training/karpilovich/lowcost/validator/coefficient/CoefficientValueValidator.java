package by.training.karpilovich.lowcost.validator.coefficient;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class PlaceCoefficientValueValidator extends Validator {
	
	private BigDecimal value;

	public PlaceCoefficientValueValidator(BigDecimal value) {
		this.value = value;
	}

	@Override
	public void validate() throws ValidatorException {
		if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_VALUE.getMessage());
		}
		continueValidate();
	}
}