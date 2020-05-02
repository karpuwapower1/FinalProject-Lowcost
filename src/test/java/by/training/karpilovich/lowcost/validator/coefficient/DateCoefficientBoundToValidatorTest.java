package by.training.karpilovich.lowcost.validator.coefficient;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class DateCoefficientBoundToValidatorTest {

	private static final Calendar DEPARTURE_DATE = new GregorianCalendar(2020, 12, 20);
	private static final Calendar BOUND_FROM = new GregorianCalendar(2020, 10, 10);
	private static final Calendar VALID_BOUND_TO = new GregorianCalendar(2020, 11, 10);
	private static final Calendar BOUND_TO_LESS_THAN_FROM = new GregorianCalendar(2020, 9, 10);
	private static final Calendar BOUND_TO_GREATER_THAN_DEPARTURE_DATE = new GregorianCalendar(2021, 12, 25);

	@Test
	public void validateTestValidBoundTo() throws ValidatorException {
		DateCoefficientBoundToValidator validator = new DateCoefficientBoundToValidator(VALID_BOUND_TO, BOUND_FROM,
				DEPARTURE_DATE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundToLessThanFrom() throws ValidatorException {
		DateCoefficientBoundToValidator validator = new DateCoefficientBoundToValidator(BOUND_TO_LESS_THAN_FROM,
				BOUND_FROM, DEPARTURE_DATE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestBoundToGreaterThanDepartureDate() throws ValidatorException {
		DateCoefficientBoundToValidator validator = new DateCoefficientBoundToValidator(
				BOUND_TO_GREATER_THAN_DEPARTURE_DATE, BOUND_FROM, DEPARTURE_DATE);
		validator.validate();
	}
}