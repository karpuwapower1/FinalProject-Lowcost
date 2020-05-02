package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.Calendar;
import java.util.GregorianCalendar;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class DateCoefficientBoundFromValidator extends Validator {

	private Calendar from;
	private Calendar departureDate;

	public DateCoefficientBoundFromValidator(Calendar from, Calendar departureDate) {
		this.from = new GregorianCalendar(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DATE));
		this.departureDate = new GregorianCalendar(departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH),
				departureDate.get(Calendar.DATE));
	}

	@Override
	public void validate() throws ValidatorException {
		if (from == null || from.compareTo(departureDate) >= 0) {
			throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_FROM.getMessage());
		}
		continueValidate();
	}
}