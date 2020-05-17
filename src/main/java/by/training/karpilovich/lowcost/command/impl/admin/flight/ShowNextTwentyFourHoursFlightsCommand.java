package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowNextTwentyFourHoursFlightsCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			setFlightsToRequest(request, getNextTwentyFourHoursFlights());
			page = Page.SHOW_FLIGHTS;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE));
		}
		return page.getAddress();
	}

	private void setFlightsToRequest(HttpServletRequest request, List<Flight> flights) {
		request.setAttribute(Attribute.FLIGHTS.toString(), flights);
	}

	private List<Flight> getNextTwentyFourHoursFlights() throws ServiceException {
		return getFlightService().searchNextTwentyForHoursFlights();
	}
}