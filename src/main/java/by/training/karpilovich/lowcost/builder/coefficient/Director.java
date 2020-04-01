package by.training.karpilovich.lowcost.builder.coefficient;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.LuggageCoefficient;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class Director {

	public DateCoefficient buildDateCoefficient(int id, int from, int to, BigDecimal coefficient) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder = buildCoefficient(builder, id, from, to, coefficient);
		return builder.getCoefficient();
	}

	public PlaceCoefficient buildPlaceCoefficient(int id, int from, int to, BigDecimal coefficient) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder = buildCoefficient(builder, id, from, to, coefficient);
		return builder.getCoefficient();
	}

	public LuggageCoefficient buildLuggageCoefficient(int id, int from, int to, BigDecimal coefficient) {
		LuggageCoefficientBuilder builder = new LuggageCoefficientBuilder();
		builder = buildCoefficient(builder, id, from, to, coefficient);
		return builder.getCoefficient();
	}

	private <T extends AbstractCoefficientBuilder> T buildCoefficient(T builder, int id, int from, int to,
			BigDecimal value) {
		builder.setBoundFrom(from);
		builder.setBoundTo(to);
		builder.setFlightId(id);
		builder.setValue(value);
		return builder;
	}

}
