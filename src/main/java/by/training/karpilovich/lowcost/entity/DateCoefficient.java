package by.training.karpilovich.lowcost.entity.coefficient;

import java.math.BigDecimal;

public class DateCoefficient extends AbstractCoefficient {
	
	private static final long serialVersionUID = 1L;
	
	public DateCoefficient() {
	}

	public DateCoefficient(int flightId, int boundFrom, int boundTo, BigDecimal value) {
		super(flightId, boundFrom, boundTo, value);
	}

}
