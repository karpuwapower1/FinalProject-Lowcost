package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.admin.flight.ShowAllFlightsCommand;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToCreateTicketPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String flightId = request.getParameter(JSPParameter.FLIGHT_ID);
		try {
			prepareSession(session, flightId);
			return getReturnedAddress(session);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new ShowAllFlightsCommand().execute(request, response);
		}
	}
	
	private void prepareSession(HttpSession session, String flightId) throws ServiceException {
		session.removeAttribute(Attribute.TICKETS.toString());
		int quantity = (Integer) session.getAttribute(Attribute.PASSENGER_QUANTITY.toString());
		Flight flight = getFlightService().getFlightById(flightId);
		getTicketService().bookTicketToFlight(flight, quantity);
		session.setAttribute(Attribute.FLIGHT.toString(), flight);
	}
	
	private String getReturnedAddress(HttpSession session) {
		if (session.getAttribute(Attribute.USER.toString()) == null) {
			session.setAttribute(Attribute.PAGE_FROM.toString(), Page.CREATE_TICKET);
			return Page.SIGN_IN.getAddress();
		}
		return Page.CREATE_TICKET.getAddress();
	}
}