package by.training.karpilovich.lowcost.builder.coefficient;

import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class PlaceCoefficientBuilder extends AbstractCoefficientBuilder {

	public PlaceCoefficientBuilder() {
		coefficient = new PlaceCoefficient();
	}

	@Override
	public PlaceCoefficient getCoefficient() {
		return (PlaceCoefficient) coefficient;
	}

}
