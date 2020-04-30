package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class DeleteFlightCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flightId = request.getParameter(JSPParameter.FLIGHT_ID);
		try {
			getFlightService().removeFlightAndReturnAllPurchasedTickets(flightId);
			return new ShowAllFlightsCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.SHOW_FLIGHTS.getAddress();
		}
	}
}