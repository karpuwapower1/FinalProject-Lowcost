package by.training.karpilovich.lowcost.validator.flight;

import java.util.Calendar;

import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class NumberAndDateAbsenceValidator extends Validator {
	
	private String number;
	private Calendar date;
	
	public NumberAndDateAbsenceValidator(String number, Calendar date) {
		this.number = number;
		this.date = date;
	}
	
	@Override
	public void validate() throws ValidatorException {
		FlightService service = ServiceFactory.getInstance().getFlightService();
		try {
			if (service.getFlightCountWithNumberAndDate(number, date) > 0) {
				throw new ValidatorException(MessageType.FLIGHT_NUMBER_AND_DATE_PRESENT_ALREADY.getMessage());
			}
		} catch (ServiceException e) {
			throw new ValidatorException(e.getMessage());
		}
		continueValidate();
	}
}