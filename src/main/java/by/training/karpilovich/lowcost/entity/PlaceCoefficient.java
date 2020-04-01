package by.training.karpilovich.lowcost.entity;

import java.math.BigDecimal;

public class PlaceCoefficient extends AbstractCoefficient {

	private static final long serialVersionUID = 1L;
	
	public PlaceCoefficient() {
	
	}

	public PlaceCoefficient(int flightId, int boundFrom, int boundTo, BigDecimal value) {
		super(flightId, boundFrom, boundTo, value);
	}

}
