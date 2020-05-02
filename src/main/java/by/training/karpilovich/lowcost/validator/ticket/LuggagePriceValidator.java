package by.training.karpilovich.lowcost.validator.ticket;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class LuggagePriceValidator extends Validator {

	private BigDecimal price;

	public LuggagePriceValidator(BigDecimal price) {
		this.price = price;
	}

	@Override
	public void validate() throws ValidatorException {
		if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidatorException(MessageType.INVALID_PRICE.getMessage());
		}
		continueValidate();
	}
}