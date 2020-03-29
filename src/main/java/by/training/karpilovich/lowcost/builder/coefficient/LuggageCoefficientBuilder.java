package by.training.karpilovich.lowcost.builder.coefficient;

import by.training.karpilovich.lowcost.entity.LuggageCoefficient;

public class LuggageCoefficientBuilder extends AbstractCoefficientBuilder {

	public LuggageCoefficientBuilder() {
		coefficient = new LuggageCoefficient();
	}

	public LuggageCoefficient getCoefficient() {
		return (LuggageCoefficient) coefficient;
	}

}
