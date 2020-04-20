package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

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
import by.training.karpilovich.lowcost.command.impl.ShowAllFlightsCommand;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToCreateTicketPageCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(RedirectToCreateTicketPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(Attribute.USER.toString()) == null) {
			return Page.SIGN_IN.getAddress();
		}
		int quantity = (Integer) session.getAttribute(Attribute.PASSENGER_QUANTITY.toString());
		String id = request.getParameter(JSPParameter.FLIGHT_ID);
		
		try {
			Flight flight = getFlightService().getFlightById(id);
			getTicketService().bookTicketToFlight(flight, quantity);
			session.setAttribute(Attribute.FLIGHT.toString(), flight);
			return Page.CREATE_TICKET.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new ShowAllFlightsCommand().execute(request, response);
		}
	}
}