package by.training.karpilovich.lowcost.command.impl.redirect;

import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;

public class RedirectToAddPlaceCoefficientPageCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		prepareToRedirect(request);
		return Page.ADD_PLACE_COEFFICIENT.getAddress();
	}

	private void prepareToRedirect(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<PlaceCoefficient> coefficients = util.takePlaceCoefficientFromSession(session);
		int boundFrom = getPlaceCoefficientService().getNextBoundFromValuePlaceCoefficient(coefficients);
		setAttributes(request, boundFrom, flight.getAvailablePlaceQuantity());
	}

	private void setAttributes(HttpServletRequest request, int boundFrom, int maxBoundValue) {
		request.setAttribute(Attribute.BOUND_FROM.toString(), boundFrom);
		request.setAttribute(Attribute.MAX_BOUND_VALUE.toString(), maxBoundValue);
	}
}