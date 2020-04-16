package by.training.karpilovich.lowcost.builder;

import java.math.BigDecimal;
import java.util.Calendar;

import by.training.karpilovich.lowcost.entity.DateCoefficient;

public class DateCoefficientBuilder {

	private DateCoefficient coefficient;
	
	public DateCoefficientBuilder() {
		coefficient = new DateCoefficient();
	}
	
	public void setFlightId(int flightId) {
		coefficient.setFlightId(flightId);
	}
	
	public void setFrom(Calendar from) {
		coefficient.setFrom(from);
	}
	
	public void setTo(Calendar to) {
		coefficient.setTo(to);
	}
	
	public void setValue(BigDecimal value) {
		coefficient.setValue(value);
	}
	
	public DateCoefficient getCoefficient() {
		return coefficient;
	}
}