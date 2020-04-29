package by.training.karpilovich.lowcost.validator.flight;

import java.util.Calendar;
import java.util.GregorianCalendar;

import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class DateValidator extends Validator {

	private Calendar date;

	public DateValidator(Calendar date) {
		this.date = date;
	}

	@Override
	public void validate() throws ValidatorException {
		if (date == null || date.compareTo(new GregorianCalendar()) < 0) {
			throw new ValidatorException(MessageType.INVALID_DATE.getMessage());
		}
		continueValidate();
	}
}