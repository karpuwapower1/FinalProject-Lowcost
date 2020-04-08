package by.training.karpilovich.lowcost.validator.city;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.validator.Validator;

public class DeleteCityValidator extends Validator {
	
	private City city;

	public DeleteCityValidator(City city) {
		this.city = city;
	}

	@Override
	public void validate() throws ValidatorException {
		FlightService service = ServiceFactory.getInstance().getFlightService();
		
		continueValidate();
	}

}
