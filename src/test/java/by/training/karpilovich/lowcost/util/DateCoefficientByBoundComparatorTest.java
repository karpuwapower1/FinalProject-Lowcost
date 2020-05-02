package by.training.karpilovich.lowcost.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.DateCoefficientBuilder;
import by.training.karpilovich.lowcost.entity.DateCoefficient;

public class DateCoefficientByBoundComparatorTest {

	private DateCoefficient coefficient;

	@Before
	public void initDateCoefficient() {
		coefficient = buildDateCoefficient(new GregorianCalendar(2020, 10, 10), new GregorianCalendar(2020, 11, 10));
	}

	@Test
	public void compreTestEqualsCoefficient() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 10, 10),
				new GregorianCalendar(2020, 11, 10));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestCoefficientBoundsInsideComparedCoefficientBounds() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 9, 10),
				new GregorianCalendar(2020, 12, 10));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestComparedCoefficientBoundsInsideCoefficientBounds() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 10, 20),
				new GregorianCalendar(2020, 10, 30));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestEqualsBoundFrom() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 10, 10),
				new GregorianCalendar(2020, 11, 30));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestEqualsBoundTo() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 9, 10),
				new GregorianCalendar(2020, 11, 10));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestCoefficientLessThanCompared() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 11, 11),
				new GregorianCalendar(2020, 12, 10));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		Assert.assertTrue(actual < 0);
	}

	@Test
	public void compreTestCoefficientGreaterThanCompared() {
		DateCoefficient compared = buildDateCoefficient(new GregorianCalendar(2020, 8, 10),
				new GregorianCalendar(2020, 10, 9));
		DateCoefficientByBoundComparator comparator = new DateCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		Assert.assertTrue(actual > 0);
	}

	private DateCoefficient buildDateCoefficient(Calendar from, Calendar to) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		return builder.getCoefficient();
	}
}