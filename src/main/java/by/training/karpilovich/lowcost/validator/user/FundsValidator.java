package by.training.karpilovich.lowcost.validator.user;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class FundsValidator extends Validator {

	private BigDecimal balance;
	private BigDecimal substracted;

	public FundsValidator(BigDecimal balance, BigDecimal substracted) {
		this.balance = balance;
		this.substracted = substracted;
	}

	@Override
	public void validate() throws ValidatorException {
		if (balance.compareTo(substracted) < 0) {
			throw new ValidatorException(MessageType.INSUFFICIENT_FUNDS.getMessage());
		}
		continueValidate();
	}
}