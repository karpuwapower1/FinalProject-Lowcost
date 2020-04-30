package by.training.karpilovich.lowcost.command.impl.admin.ticket;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String id = request.getParameter(JSPParameter.FLIGHT_ID);
		HttpSession session = request.getSession();
		try {
			Flight flight = getFlightService().getFlightById(id);
			List<Ticket> tickets = getTicketService().getAllTicketsToFlight(flight);
			session.setAttribute(Attribute.TICKETS.toString(), tickets);
			return Page.SHOW_TICKET.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToDefaultPageCommand().execute(request, response);
		}
	}
}