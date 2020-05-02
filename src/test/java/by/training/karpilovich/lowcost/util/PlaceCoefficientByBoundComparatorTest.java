package by.training.karpilovich.lowcost.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.PlaceCoefficientBuilder;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientByBoundComparatorTest {

	private PlaceCoefficient coefficient;

	@Before
	public void initPlaceCoefficient() {
		coefficient = buildPlaceCoefficient(20, 50);
	}

	@Test
	public void compreTestEqualsCoefficient() {
		PlaceCoefficient compared = buildPlaceCoefficient(20, 50);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestCoefficientBoundsInsideComparedCoefficientBounds() {
		PlaceCoefficient compared = buildPlaceCoefficient(10, 60);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestComparedCoefficientBoundsInsideCoefficientBounds() {
		PlaceCoefficient compared = buildPlaceCoefficient(30, 40);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestEqualsBoundFrom() {
		PlaceCoefficient compared = buildPlaceCoefficient(20, 60);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestEqualsBoundTo() {
		PlaceCoefficient compared = buildPlaceCoefficient(10, 50);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		int expected = 0;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void compreTestCoefficientLessThanCompared() {
		PlaceCoefficient compared = buildPlaceCoefficient(51, 100);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		Assert.assertTrue(actual < 0);
	}

	@Test
	public void compreTestCoefficientGreaterThanCompared() {
		PlaceCoefficient compared = buildPlaceCoefficient(10, 19);
		PlaceCoefficientByBoundComparator comparator = new PlaceCoefficientByBoundComparator();
		int actual = comparator.compare(coefficient, compared);
		Assert.assertTrue(actual > 0);
	}

	private PlaceCoefficient buildPlaceCoefficient(int from, int to) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		return builder.getCoefficient();
	}
}