package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.Calendar;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class DateCoefficientBoundToValidator extends Validator {

	private Calendar from;
	private Calendar to;
	private Calendar departureDate;

	public DateCoefficientBoundToValidator(Calendar to, Calendar from, Calendar departureDate) {
		this.to = to;
		this.from = from;
		this.departureDate = departureDate;
	}

	@Override
	public void validate() throws ValidatorException {
		if (to == null || to.compareTo(from) <= 0 || to.compareTo(departureDate) > 0) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_TO.getMessage());
		}
		continueValidate();
	}
}