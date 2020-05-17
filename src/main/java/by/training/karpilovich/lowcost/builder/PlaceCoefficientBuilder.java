package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientBuilder {

	private PlaceCoefficient coefficient;

	public PlaceCoefficientBuilder() {
		coefficient = new PlaceCoefficient();
	}

	public PlaceCoefficientBuilder setFlightId(int flightId) {
		coefficient.setFlightId(flightId);
		return this;
	}

	public PlaceCoefficientBuilder setFrom(int from) {
		coefficient.setFrom(from);
		return this;
	}

	public PlaceCoefficientBuilder setTo(int to) {
		coefficient.setTo(to);
		return this;
	}

	public PlaceCoefficientBuilder setValue(BigDecimal value) {
		coefficient.setValue(value);
		return this;
	}

	public PlaceCoefficient getCoefficient() {
		return coefficient;
	}
}