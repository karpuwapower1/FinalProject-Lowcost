package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class DateCoefficientBoundFromValidatorTest {

	private static final Calendar FROM_COEFFICIENT_VALUE = new GregorianCalendar();

	@Test
	public void validateFromValueLessThanDepartureDate() throws ValidatorException {
		Calendar departureDate = addDayTo(FROM_COEFFICIENT_VALUE);
		DateCoefficientBoundFromValidator validator = new DateCoefficientBoundFromValidator(FROM_COEFFICIENT_VALUE,
				departureDate);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateFromValueGreaterThanDepartureDate() throws ValidatorException {
		Calendar departureDate = substractYearFrom(FROM_COEFFICIENT_VALUE);
		DateCoefficientBoundFromValidator validator = new DateCoefficientBoundFromValidator(FROM_COEFFICIENT_VALUE,
				departureDate);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateFromValueIsNull() throws ValidatorException {
		Calendar departureDate = substractYearFrom(FROM_COEFFICIENT_VALUE);
		DateCoefficientBoundFromValidator validator = new DateCoefficientBoundFromValidator(FROM_COEFFICIENT_VALUE,
				departureDate);
		validator.validate();
	}

	private Calendar addDayTo(Calendar calendar) {
		Calendar future = (Calendar) calendar.clone();
		int year = future.get(Calendar.YEAR);
		future.set(Calendar.YEAR, ++year);
		return future;
	}

	private Calendar substractYearFrom(Calendar calendar) {
		Calendar future = (Calendar) calendar.clone();
		int year = future.get(Calendar.YEAR);
		future.set(Calendar.YEAR, --year);
		return future;
	}
}