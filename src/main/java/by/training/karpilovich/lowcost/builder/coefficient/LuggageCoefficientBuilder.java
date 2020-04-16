package by.training.karpilovich.lowcost.builder.coefficient;

import java.math.BigDecimal;

import by.training.karpilovich.lowcost.entity.coefficient.AbstractCoefficient;

public abstract class AbstractCoefficientBuilder {
	
	protected AbstractCoefficient coefficient;
	
	public void setFlightId(int flightId) {
		coefficient.setFlightId(flightId);
	}
	
	public void setBoundFrom(int boundFrom) {
		coefficient.setBoundFrom(boundFrom);
	}
	
	public void setBoundTo(int boundTo) {
		coefficient.setBoundTo(boundTo);
	}
	
	public void setValue(BigDecimal value) {
		coefficient.setValue(value);
	}
	
	abstract <T extends AbstractCoefficient> T getCoefficient();

}
