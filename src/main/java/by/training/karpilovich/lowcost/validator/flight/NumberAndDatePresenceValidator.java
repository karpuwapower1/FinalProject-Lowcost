package by.training.karpilovich.lowcost.validator.flight;

import java.util.Calendar;

import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class NumberAndDatePresenceValidator extends Validator {
	
	private String number;
	private Calendar date;
	
	public NumberAndDatePresenceValidator(String number, Calendar date) {
		this.number = number;
		this.date = date;
	}
	
	@Override
	public void validate() throws ValidatorException {
		FlightService service = ServiceFactory.getInstance().getFlightService();
		try {
			if (service.getFlightCountWithNumberAndDate(number, date) == 0) {
				throw new ValidatorException(MessageType.NO_SUCH_FLIGHT.getMessage());
			}
		} catch (ServiceException e) {
			throw new ValidatorException(e.getMessage());
		}
		continueValidate();
	}
}