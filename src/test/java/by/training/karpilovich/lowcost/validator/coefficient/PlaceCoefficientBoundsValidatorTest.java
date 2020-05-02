package by.training.karpilovich.lowcost.validator.coefficient;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.builder.PlaceCoefficientBuilder;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.PlaceCoefficientByBoundComparator;

public class PlaceCoefficientBoundsValidatorTest {

	private static final SortedSet<PlaceCoefficient> COEFFICIENTS = new TreeSet<>(
			new PlaceCoefficientByBoundComparator());
	private static final int VALID_BOUND_FROM = 51;
	private static final int VALID_BOUND_TO = 61;
	private static final int INVALID_BOUND_FROM = 41;
	private static final int INVALID_BOUND_TO = 41;

	@Before
	public void initCoefficients() {
		COEFFICIENTS.add(buildPlaceCoefficient(0, 20, new BigDecimal("1.4")));
		COEFFICIENTS.add(buildPlaceCoefficient(21, 50, new BigDecimal("1.2")));
	}

	@Test
	public void validateTestValidBoundFromAndTo() throws ValidatorException {
		PlaceCoefficientBoundsValidator validator = new PlaceCoefficientBoundsValidator(VALID_BOUND_FROM,
				VALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundFrom() throws ValidatorException {
		PlaceCoefficientBoundsValidator validator = new PlaceCoefficientBoundsValidator(INVALID_BOUND_FROM,
				VALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundTo() throws ValidatorException {
		PlaceCoefficientBoundsValidator validator = new PlaceCoefficientBoundsValidator(VALID_BOUND_FROM,
				INVALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	@Test(expected = ValidatorException.class)
	public void validateTestInvalidBoundFromAndTo() throws ValidatorException {
		PlaceCoefficientBoundsValidator validator = new PlaceCoefficientBoundsValidator(INVALID_BOUND_FROM,
				INVALID_BOUND_TO, COEFFICIENTS);
		validator.validate();
	}

	private PlaceCoefficient buildPlaceCoefficient(int from, int to, BigDecimal value) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}
}