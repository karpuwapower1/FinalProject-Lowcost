package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientBuilder {

	private PlaceCoefficient coefficient;

	public PlaceCoefficientBuilder() {
		coefficient = new PlaceCoefficient();
	}

	public void setFlightId(int flightId) {
		coefficient.setFlightId(flightId);
	}

	public void setFrom(int from) {
		coefficient.setFrom(from);
	}

	public void setTo(int to) {
		coefficient.setTo(to);
	}

	public void setValue(BigDecimal value) {
		coefficient.setValue(value);
	}

	public PlaceCoefficient getCoefficient() {
		return coefficient;
	}
}