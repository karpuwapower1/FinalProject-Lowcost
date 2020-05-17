package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddPlaceCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddPlaceCoefficientCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address = null;
		HttpSession session = request.getSession();
		SortedSet<PlaceCoefficient> coefficients = util.takePlaceCoefficientFromSession(session);
		try {
			addCoefficientToSet(request, coefficients);
			session.setAttribute(Attribute.PLACE_COEFFICIENT.toString(), coefficients);
			address = Page.FLIGHT_PREVIEW.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = new RedirectToAddPlaceCoefficientPageCommand().execute(request, response);
		}
		return address;
	}
	
	private void addCoefficientToSet(HttpServletRequest request, SortedSet<PlaceCoefficient> coefficients)
			throws ServiceException {
		getPlaceCoefficientService().addPlaceCoefficientToSet(coefficients, createPlaceCoeffient(request));
	}

	private PlaceCoefficient createPlaceCoeffient(HttpServletRequest request) throws ServiceException {
		String to = request.getParameter(JSPParameter.BOUND_TO);
		String from = request.getParameter(JSPParameter.BOUND_FROM);
		String value = request.getParameter(JSPParameter.VALUE);
		Flight flight = (Flight) request.getSession().getAttribute(Attribute.FLIGHT.toString());
		return getPlaceCoefficientService().createPlaceCoefficient(flight.getAvailablePlaceQuantity(), from, to, value);
	}
}