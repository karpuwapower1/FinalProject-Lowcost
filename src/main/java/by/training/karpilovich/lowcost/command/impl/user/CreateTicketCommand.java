package by.training.karpilovich.lowcost.command.impl.user;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class CreateTicketCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		countDown(request);
		List<Ticket> reserved = util.takeTicketsFromSession(request.getSession());
		Page page = null;
		try {
			createAndAddTicketToReserved(request, reserved);
			setAttributes(request, reserved);
			page = getReturnedPage(reserved, request);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			removeAttributes(request);
			page = Page.SHOW_FLIGHTS;
		}
		return page.getAddress();
	}

	private void countDown(HttpServletRequest request) {
		CountDownLatch countDownLatch = (CountDownLatch) request.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		countDownLatch.countDown();
	}

	private void setAttributes(HttpServletRequest request, List<Ticket> reserved) throws ServiceException {
		request.setAttribute(Attribute.TOTAL_PRICE.toString(), getTicketService().countTicketPrice(reserved));
		request.getSession().setAttribute(Attribute.TICKETS.toString(), reserved);
	}

	private void createAndAddTicketToReserved(HttpServletRequest request, List<Ticket> reserved)
			throws ServiceException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER.toString());
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		String firstName = request.getParameter(JSPParameter.FIRST_NAME);
		String lastName = request.getParameter(JSPParameter.LAST_NAME);
		String passportNumber = request.getParameter(JSPParameter.PASSPORT_NUMBER);
		String luggageQuantity = request.getParameter(JSPParameter.LUGGAGE);
		String primaryBoarding = request.getParameter(JSPParameter.PRIMARY_BOARDING);
		reserved.add(getTicketService().createTicket(user, flight, firstName, lastName, passportNumber, luggageQuantity,
				primaryBoarding));
	}

	private Page getReturnedPage(List<Ticket> tickets, HttpServletRequest request) {
		return tickets.size() < (Integer) request.getSession().getAttribute(Attribute.PASSENGER_QUANTITY.toString())
				? Page.CREATE_TICKET
				: Page.BYE_TICKETS;
	}

	private void removeAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Attribute.TICKETS.toString());
		session.removeAttribute(Attribute.PASSENGER_QUANTITY.toString());
		session.removeAttribute(Attribute.FLIGHT.toString());
		session.removeAttribute(Attribute.COUNT_DOWN_LATCH.toString());
	}
}