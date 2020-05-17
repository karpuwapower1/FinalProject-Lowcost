package by.training.karpilovich.lowcost.command.impl.general;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class SearchFlightCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			setFlightsToRequest(request, findFlights(request));
			setPassengerQuantityToSession(request);
			page = Page.SHOW_FLIGHTS;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.DEFAULT;
		}
		return page.getAddress();
	}

	private void setFlightsToRequest(HttpServletRequest request, List<Flight> flights) {
		request.setAttribute(Attribute.FLIGHTS.toString(), flights);
	}

	private List<Flight> findFlights(HttpServletRequest request) throws ServiceException {
		String cityFrom = request.getParameter(JSPParameter.COUNTRY_FROM);
		String cityTo = request.getParameter(JSPParameter.COUNTRY_TO);
		String date = request.getParameter(JSPParameter.DATE);
		String quantity = request.getParameter(JSPParameter.QUANTITY);
		City from = getCityService().getCityById(cityFrom);
		City to = getCityService().getCityById(cityTo);
		FlightService flightService = getFlightService();
		return flightService.searchFlights(from, to, date, quantity);
	}

	private void setPassengerQuantityToSession(HttpServletRequest request) {
		request.getSession().setAttribute(Attribute.PASSENGER_QUANTITY.toString(),
				Integer.parseInt(request.getParameter(JSPParameter.QUANTITY)));
	}
}