package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;
import java.util.Calendar;

import by.training.karpilovich.lowcost.entity.DateCoefficient;

public class DateCoefficientBuilder {

	private DateCoefficient coefficient;

	public DateCoefficientBuilder() {
		coefficient = new DateCoefficient();
	}

	public DateCoefficientBuilder setFlightId(int flightId) {
		coefficient.setFlightId(flightId);
		return this;
	}

	public DateCoefficientBuilder setFrom(Calendar from) {
		coefficient.setFrom(from);
		return this;
	}

	public DateCoefficientBuilder setTo(Calendar to) {
		coefficient.setTo(to);
		return this;
	}

	public DateCoefficientBuilder setValue(BigDecimal value) {
		coefficient.setValue(value);
		return this;
	}

	public DateCoefficient getCoefficient() {
		return coefficient;
	}
}