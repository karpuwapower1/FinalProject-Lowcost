package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class ShowAllFlightsCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(ShowAllFlightsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FlightService flightService = getFlightService();
		HttpSession session = request.getSession();
		try {
			List<Flight> flights = flightService.getAllFlights();
			for (Flight flight : flights) {
				LOGGER.debug(flight.toString());
			}
			session.setAttribute(Attribute.FLIGHTS.toString(), flights);
			return Page.SHOW_FLIGHTS.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.DEFAULT.getAddress();
		}
	}
}