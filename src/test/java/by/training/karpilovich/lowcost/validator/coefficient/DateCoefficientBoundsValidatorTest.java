package by.training.karpilovich.lowcost.validator.coefficient;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.DateCoefficientBuilder;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.DateCoefficientByBoundComparator;

public class DateCoefficientBoundsValidatorTest {

	private static final SortedSet<DateCoefficient> COEFFICIENTS = new TreeSet<>(
			new DateCoefficientByBoundComparator());

	private static final Calendar VALID_BOUND_FROM = new GregorianCalendar(2020, 8, 11);
	private static final Calendar VALID_BOUND_TO = new GregorianCalendar(2020, 9, 11);
	private static final Calendar INVALID_BOUND_FROM = new GregorianCalendar(2020, 7, 11);
	private static final Calendar INVALID_BOUND_TO = new GregorianCalendar(2020, 7, 12);

	@Before
	public void initCoefficients() {
		COEFFICIENTS.add(buildDateCoefficient(new GregorianCalendar(2020, 4, 10), new GregorianCalendar(2020, 6, 10),
				new BigDecimal("1.2")));
		COEFFICIENTS.add(buildDateCoefficient(new GregorianCalendar(2020, 6, 11), new GregorianCalendar(2020, 8, 10),
				new BigDecimal("1.4")));
	}

	@Test
	public void validateTestBundsValid() throws ValidatorException {
		DateCoefficientBoundsValidator validator = new DateCoefficientBoundsValidator(VALID_BOUND_FROM, VALID_BOUND_TO,
				COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundFrom() throws ValidatorException {
		DateCoefficientBoundsValidator validator = new DateCoefficientBoundsValidator(INVALID_BOUND_FROM,
				VALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundTo() throws ValidatorException {
		DateCoefficientBoundsValidator validator = new DateCoefficientBoundsValidator(VALID_BOUND_FROM,
				INVALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundFromAndTo() throws ValidatorException {
		DateCoefficientBoundsValidator validator = new DateCoefficientBoundsValidator(INVALID_BOUND_FROM,
				INVALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	private DateCoefficient buildDateCoefficient(Calendar from, Calendar to, BigDecimal value) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}
}