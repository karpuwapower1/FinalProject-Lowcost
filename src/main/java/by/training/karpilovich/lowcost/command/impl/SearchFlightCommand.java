package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class SearchFlightCommand implements Command {

	private static final String COUNTRY_FROM_PARAMETER_NAME = "departure";
	private static final String COUNTRY_TO_PARAMETER_NAME = "destination";
	private static final String DATE_PARAMETER_NAME = "departureDate";
	private static final String QUANTITY_PARAMETER_NAME = "quantity";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = null;
		try {
			Set<Flight> flights = findFlights(request);
			session.setAttribute(AttributeName.FLIGHTS.getName(), flights);
			page = Page.RESULT;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return page == null ? new RedirectToDefaultPageCommand().execute(request, response) : page.getAddress();
	}

	private Set<Flight> findFlights(HttpServletRequest request) throws ServiceException {
		String countryFrom = request.getParameter(COUNTRY_FROM_PARAMETER_NAME);
		String countryTo = request.getParameter(COUNTRY_TO_PARAMETER_NAME);
		String date = request.getParameter(DATE_PARAMETER_NAME);
		System.out.println(date);
		String quantity = request.getParameter(QUANTITY_PARAMETER_NAME);
		FlightService flightService = getFlightService();
		Set<Flight> flights = flightService.getFlight(countryFrom, countryTo, date, quantity);
		return flights;
	}

}
