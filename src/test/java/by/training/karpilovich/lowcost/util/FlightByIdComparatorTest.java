package by.training.karpilovich.lowcost.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.entity.Flight;

public class FlightByIdComparatorTest {

	private Flight flight;
	private static final int ID = 10;
	private static final int EQUAL_ID = 10;
	private static final int LESS_ID = 5;
	private static final int GREATER_ID = 15;

	@Before
	public void initFlight() {
		flight = buildFlight(ID);
	}

	@Test
	public void compareTestEqualIds() {
		Flight compared = buildFlight(EQUAL_ID);
		FlightByIdComparator comparator = new FlightByIdComparator();
		int actual = comparator.compare(flight, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compareTestFlightIdGreater() {
		Flight compared = buildFlight(LESS_ID);
		FlightByIdComparator comparator = new FlightByIdComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual > 0);
	}

	@Test
	public void compareTestFlightIdLess() {
		Flight compared = buildFlight(GREATER_ID);
		FlightByIdComparator comparator = new FlightByIdComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual < 0);
	}

	private Flight buildFlight(int id) {
		FlightBuilder builder = new FlightBuilder();
		builder.setId(id);
		return builder.getFlight();
	}
}