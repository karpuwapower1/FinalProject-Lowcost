package by.training.karpilovich.lowcost.validator.ticket;

import org.junit.Before;
import org.junit.Test;

import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ValidatorException;

public class TicketAndUserIdentityEmailValidatorTest {
	
	private static final User NULL_USER = null;
	private static final Ticket NULL_TICKET = null;
	private static final String EMAIL = "1234qwerty@gmail.com";
	private static final String DIFFERENT_EMAIL = "5678uiop@gmail.com";
	
	private User user = new User();
	private Ticket ticket = new Ticket();
	
	@Before
	public void setEmails() {
		user.setEmail(EMAIL);
		ticket.setEmail(EMAIL);
	}
	
	@Test
	public void validateTestEqualsEmail() throws ValidatorException {
		TicketAndUserIdentityEmailValidator validator = new TicketAndUserIdentityEmailValidator(user, ticket);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestNullUser() throws ValidatorException {
		TicketAndUserIdentityEmailValidator validator = new TicketAndUserIdentityEmailValidator(NULL_USER, ticket);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestNullTicket() throws ValidatorException {
		TicketAndUserIdentityEmailValidator validator = new TicketAndUserIdentityEmailValidator(user, NULL_TICKET);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestDifferentEmails() throws ValidatorException {
		ticket.setEmail(DIFFERENT_EMAIL);
		TicketAndUserIdentityEmailValidator validator = new TicketAndUserIdentityEmailValidator(user, ticket);
		validator.validate();
	}
	
	@Test(expected = ValidatorException.class)
	public void validateTestNullUsersEmailEmails() throws ValidatorException {
		user.setEmail(null);
		TicketAndUserIdentityEmailValidator validator = new TicketAndUserIdentityEmailValidator(user, ticket);
		validator.validate();
	}
}