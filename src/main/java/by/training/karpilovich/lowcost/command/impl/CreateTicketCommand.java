package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class CreateTicketCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(CreateTicketCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();	
		List<Ticket> tickets = getTickets(session);
		int quantity = (Integer)session.getAttribute(Attribute.PASSENGER_QUANTITY.toString()); 
		CountDownLatch countDownLatch = (CountDownLatch) session.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		countDownLatch.countDown();
		try {
			Ticket ticket = createTicket(request);
			tickets.add(ticket);
			session.setAttribute(Attribute.TICKETS.toString(), tickets);
			return tickets.size() < quantity ? Page.CREATE_TICKET.getAddress() : Page.BYE_TICKETS.getAddress();
		} catch (ServiceException e) {
			LOGGER.debug(e);
			setErrorMessage(request, response.getLocale(), e.getMessage());
			session.removeAttribute(Attribute.TICKETS.toString());
			session.removeAttribute(Attribute.PASSENGER_QUANTITY.toString());
			session.removeAttribute(Attribute.FLIGHT.toString());
			return Page.SHOW_FLIGHTS.getAddress();
		}
	}
	
	private Ticket createTicket(HttpServletRequest request) throws ServiceException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER.toString());
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		String firstName = request.getParameter(JSPParameter.FIRST_NAME);
		String lastName = request.getParameter(JSPParameter.LAST_NAME);
		String passportNumber = request.getParameter(JSPParameter.PASSPORT_NUMBER);
		String luggageQuantity = request.getParameter(JSPParameter.LUGGAGE);
		String primaryBoarding = request.getParameter(JSPParameter.PRIMARY_BOARDING);
		return getTicketService().createTicket(user, flight, firstName, lastName, passportNumber, luggageQuantity, primaryBoarding);
	}

	private List<Ticket> getTickets(HttpSession session) {
		List<?> objects = (List<?>) session.getAttribute(Attribute.TICKETS.toString());
		if (objects == null) {
			return new ArrayList<>();
		}
		List<Ticket> tickets = new ArrayList<>(objects.size());
		for (Object object : objects) {
			tickets.add((Ticket) object);
		}
		return tickets;
	}
}