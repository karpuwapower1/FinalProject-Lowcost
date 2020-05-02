package by.training.karpilovich.lowcost.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.entity.Flight;

public class FligthByDepartureDateComparatorTest {

	private Flight flight;
	private static final Calendar DATE = new GregorianCalendar(2020, 10, 10);
	private static final Calendar EQUAL_DATE = new GregorianCalendar(2020, 10, 10);
	private static final Calendar LESS_DATE = new GregorianCalendar(2020, 10, 9);
	private static final Calendar GREATER_DATE = new GregorianCalendar(2020, 10, 11);

	@Before
	public void initFlight() {
		flight = buildFlight(DATE);
	}

	@Test
	public void compareTestEqualDates() {
		Flight compared = buildFlight(EQUAL_DATE);
		FlightByDepartureDateComparator comparator = new FlightByDepartureDateComparator();
		int actual = comparator.compare(flight, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compareTestFlightDateGreater() {
		Flight compared = buildFlight(LESS_DATE);
		FlightByDepartureDateComparator comparator = new FlightByDepartureDateComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual > 0);
	}

	@Test
	public void compareTestFlightDateLess() {
		Flight compared = buildFlight(GREATER_DATE);
		FlightByDepartureDateComparator comparator = new FlightByDepartureDateComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual < 0);
	}

	private Flight buildFlight(Calendar date) {
		FlightBuilder builder = new FlightBuilder();
		builder.setDate(date);
		return builder.getFlight();
	}
}