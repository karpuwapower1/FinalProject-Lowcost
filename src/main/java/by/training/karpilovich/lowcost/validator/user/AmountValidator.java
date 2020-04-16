package by.training.karpilovich.lowcost.validator.user;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class AmountValidator extends Validator {

	private BigDecimal amount;

	public AmountValidator(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public void validate() throws ValidatorException {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidatorException(MessageType.INVALID_AMOUNT.getMessage());
		}
		continueValidate();
	}
}