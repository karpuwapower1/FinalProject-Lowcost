package by.training.karpilovich.lowcost.entity;

import java.math.BigDecimal;

public class LuggageCoefficient extends AbstractCoefficient {

	private static final long serialVersionUID = 1L;
	
	public LuggageCoefficient() {
		
	}

	public LuggageCoefficient(int flightId, int boundFrom, int boundTo, BigDecimal value) {
		super(flightId, boundFrom, boundTo, value);
	}

}
