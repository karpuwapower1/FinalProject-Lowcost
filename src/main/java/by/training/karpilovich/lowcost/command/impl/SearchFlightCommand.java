package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.Set;

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
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class SearchFlightCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = null;
		try {
			Set<Flight> flights = findFlights(request);
			session.setAttribute(Attribute.FLIGHTS.toString(), flights);
			page = Page.RESULT;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return page == null ? new RedirectToDefaultPageCommand().execute(request, response) : page.getAddress();
	}

	private Set<Flight> findFlights(HttpServletRequest request) throws ServiceException {
		String countryFrom = request.getParameter(JSPParameter.COUNTRY_FROM);
		String countryTo = request.getParameter(JSPParameter.COUNTRY_TO);
		String date = request.getParameter(JSPParameter.DATE);
		String quantity = request.getParameter(JSPParameter.QUANTITY);
		FlightService flightService = getFlightService();
		return flightService.getFlight(countryFrom, countryTo, date, quantity);
	}
}