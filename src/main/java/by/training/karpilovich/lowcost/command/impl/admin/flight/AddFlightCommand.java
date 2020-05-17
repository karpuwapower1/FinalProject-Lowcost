package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddFlightCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address = null;
		try {
			addFlightAndCoefficients(request);
			removeAttributes(request);
			address = new ShowAllFlightsCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = Page.FLIGHT_PREVIEW.getAddress();
		}
		return address;
	}

	private void addFlightAndCoefficients(HttpServletRequest request) throws ServiceException {
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<DateCoefficient> dateCoefficients = util.takeDateCoefficientFromSession(session);
		SortedSet<PlaceCoefficient> placeCoefficients = util.takePlaceCoefficientFromSession(session);
		getFlightService().addFlight(flight);
		getDateCoefficientService().saveCoefficients(flight, dateCoefficients);
		getPlaceCoefficientService().saveCoefficients(flight, placeCoefficients);
	}

	private void removeAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Attribute.FLIGHT.toString());
		session.removeAttribute(Attribute.DATE_COEFFICIENT.toString());
		session.removeAttribute(Attribute.PLACE_COEFFICIENT.toString());
	}
}