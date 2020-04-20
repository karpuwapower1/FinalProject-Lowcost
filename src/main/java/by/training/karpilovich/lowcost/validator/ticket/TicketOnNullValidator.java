package by.training.karpilovich.lowcost.validator.ticket;

import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class TicketOnNullValidator extends Validator {

	private Ticket ticket;

	public TicketOnNullValidator(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public void validate() throws ValidatorException {
		if (ticket == null) {
			throw new ValidatorException(MessageType.NO_SUCH_TICKET.getMessage());
		}
		continueValidate();
	}
}