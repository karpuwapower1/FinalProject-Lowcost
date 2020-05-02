package by.training.karpilovich.lowcost.validator.ticket;

import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;

public class TicketAndUserIdentityEmailValidator extends Validator {
	
	private User user;
	private Ticket ticket;
	
	public TicketAndUserIdentityEmailValidator(User user, Ticket ticket) {
		this.user = user;
		this.ticket = ticket;
	}
	@Override
	public void validate() throws ValidatorException {
		if (user == null || ticket == null || user.getEmail() == null || !user.getEmail().equals(ticket.getEmail())) {
			throw new ValidatorException(MessageType.USER_AND_TICKET_DONT_MATCH.getMessage());
		}
		continueValidate();
	}
}