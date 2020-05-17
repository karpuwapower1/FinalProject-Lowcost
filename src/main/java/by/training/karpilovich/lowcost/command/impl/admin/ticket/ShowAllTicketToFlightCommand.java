package by.training.karpilovich.lowcost.command.impl.admin.ticket;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowAllTicketToFlightCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address = null;
		try {
			setTicketToRequest(request, getTicketsToFlight(getFlightFromRequest(request)));
			address = Page.SHOW_TICKET.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = new RedirectToDefaultPageCommand().execute(request, response);
		}
		return address;
	}
	
	private void setTicketToRequest(HttpServletRequest request, List<Ticket> tickets) {
		request.setAttribute(Attribute.TICKETS.toString(), tickets);
	}
	
	private List<Ticket> getTicketsToFlight(Flight flight) throws ServiceException {
		return getTicketService().getAllTicketsToFlight(flight);
	}
	
	private Flight getFlightFromRequest(HttpServletRequest request) throws ServiceException {
		String id = request.getParameter(JSPParameter.FLIGHT_ID);
		return getFlightService().getFlightById(id);
	}
}