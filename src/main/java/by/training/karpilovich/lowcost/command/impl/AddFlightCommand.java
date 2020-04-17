package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.CoefficientCreatorHelper;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.FlightService;

public class AddFlightCommand extends CoefficientCreatorHelper implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<DateCoefficient> dateCoefficients = getDateCoefficientFromSession(session);
		SortedSet<PlaceCoefficient> placeCoefficients = getPlaceCoefficientFromSession(session);
		FlightService service = getFlightService();
		try {
			service.addFlight(flight, placeCoefficients, dateCoefficients);
			session.removeAttribute(Attribute.FLIGHT.toString());
			session.removeAttribute(Attribute.DATE_COEFFICIENT.toString());
			session.removeAttribute(Attribute.PLACE_COEFFICIENT.toString());
			return Page.CREATE_FLIGHT.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.FLIGHT_PREVIEW.getAddress();
		}
	}
}