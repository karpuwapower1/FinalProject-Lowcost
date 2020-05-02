package by.training.karpilovich.lowcost.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.entity.Flight;

public class FlightByTicketPriceComparatorTest {

	private Flight flight;
	private static final BigDecimal PRICE = new BigDecimal("10.0");
	private static final BigDecimal EQUAL_PRICE = new BigDecimal("10.0");
	private static final BigDecimal LESS_PRICE = new BigDecimal("5.0");
	private static final BigDecimal GREATER_PRICE = new BigDecimal("15.0");

	@Before
	public void initFlight() {
		flight = buildFlight(PRICE);
	}

	@Test
	public void compareTestEqualPrices() {
		Flight compared = buildFlight(EQUAL_PRICE);
		FlightByTicketPriceComparator comparator = new FlightByTicketPriceComparator();
		int actual = comparator.compare(flight, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compareTestFlightPriceGreater() {
		Flight compared = buildFlight(LESS_PRICE);
		FlightByTicketPriceComparator comparator = new FlightByTicketPriceComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual > 0);
	}

	@Test
	public void compareTestFlightPriceLess() {
		Flight compared = buildFlight(GREATER_PRICE);
		FlightByTicketPriceComparator comparator = new FlightByTicketPriceComparator();
		int actual = comparator.compare(flight, compared);
		Assert.assertTrue(actual < 0);
	}

	private Flight buildFlight(BigDecimal price) {
		FlightBuilder builder = new FlightBuilder();
		builder.setPrice(price);
		return builder.getFlight();
	}
}