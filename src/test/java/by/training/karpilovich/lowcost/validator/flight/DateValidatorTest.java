package by.training.karpilovich.lowcost.validator.flight;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import by.training.karpilovich.lowcost.exception.ValidatorException;

public class DateValidatorTest {

	private static final Calendar VALID_DATE = new GregorianCalendar(2020, 5, 1);
	private static final Calendar NULL_DATE = null;

	@Test
	public void validateTestValidDate() throws ValidatorException {
		DateValidator validator = new DateValidator(VALID_DATE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestNullDate() throws ValidatorException {
		DateValidator validator = new DateValidator(NULL_DATE);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidDate() throws ValidatorException {
		DateValidator validator = new DateValidator(getInvalidDate());
		validator.validate();
	}

	private Calendar getInvalidDate() {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, --year);
		return calendar;
	}
}