package by.training.karpilovich.lowcost.validator.ticket;

import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberAndDatePresenceValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberValidator;

public class FlightNumberAndDateValidator extends Validator {

	private Flight flight;

	public FlightNumberAndDateValidator(Flight flight) {
		this.flight = flight;
	}

	@Override
	public void validate() throws ValidatorException {
		if (flight == null) {
			throw new ValidatorException(MessageType.NULL_FLIGHT.getMessage());
		}
		Validator flightNumberValidator = new NumberValidator(flight.getNumber());
		Validator flightDateValidator = new DateValidator(flight.getDate());
		Validator flightWithNumberAndDatePresenceValidator = new NumberAndDatePresenceValidator(flight.getNumber(),
				flight.getDate());
		flightNumberValidator.setNext(flightDateValidator);
		flightDateValidator.setNext(flightWithNumberAndDatePresenceValidator);
		flightNumberValidator.validate();
		continueValidate();
	}
}