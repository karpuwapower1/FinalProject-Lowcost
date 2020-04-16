package by.training.karpilovich.lowcost.command.impl.redirect;

import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.CoefficientCreatorHelper;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToAddPlaceCoefficientPageCommand extends CoefficientCreatorHelper implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<PlaceCoefficient> coefficients = getPlaceCoefficientFromSession(session);
		try {
			int boundFrom = getFlightCreatorService().getNextBoundFromValuePlaceCoefficient(coefficients);
			request.setAttribute(Attribute.BOUND_FROM.toString(), boundFrom);
			request.setAttribute(Attribute.MAX_BOUND_VALUE.toString(), flight.getAvailablePlaceQuantity());
			return Page.ADD_PLACE_COEFFICIENT.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.CREATE_FLIGHT.getAddress();
		}
	}
}