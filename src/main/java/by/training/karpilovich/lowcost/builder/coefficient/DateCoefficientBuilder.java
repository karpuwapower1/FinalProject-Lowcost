package by.training.karpilovich.lowcost.builder.coefficient;

import by.training.karpilovich.lowcost.entity.coefficient.DateCoefficient;

public class DateCoefficientBuilder extends AbstractCoefficientBuilder {

	public DateCoefficientBuilder() {
		coefficient = new DateCoefficient();
	}

	@Override
	public DateCoefficient getCoefficient() {
		return (DateCoefficient) coefficient;
	}

}
