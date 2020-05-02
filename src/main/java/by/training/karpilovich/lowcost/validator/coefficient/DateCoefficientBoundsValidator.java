package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.Calendar;
import java.util.SortedSet;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class DateCoefficientBoundsValidator extends Validator {
	
	private Calendar boundFrom;
	private Calendar boundTo;
	private SortedSet<DateCoefficient> coefficients;
	
	public DateCoefficientBoundsValidator(Calendar boundFrom, Calendar boundTo, SortedSet<DateCoefficient> coefficients) {
		this.boundFrom = boundFrom;
		this.boundTo = boundTo;
		this.coefficients = coefficients;
	}

	@Override
	public void validate() throws ValidatorException {
		for (DateCoefficient coefficient : coefficients) {
			if (boundFrom.compareTo(coefficient.getFrom()) >= 0 && boundFrom.compareTo(coefficient.getTo()) <= 0) {
				throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_FROM.getMessage());
			}
			if (boundTo.compareTo(coefficient.getFrom()) >= 0 && boundTo.compareTo(coefficient.getTo()) <= 0) {
				throw new ValidatorException(MessageType.INVALID_COEFFICINET_BOUND_TO.getMessage());
			}
		}
		continueValidate();
	}
}