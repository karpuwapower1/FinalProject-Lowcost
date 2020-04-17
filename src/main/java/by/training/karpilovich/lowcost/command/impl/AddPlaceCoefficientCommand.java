package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddPlaceCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.util.CoefficientCreatorHelper;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddPlaceCoefficientCommand extends CoefficientCreatorHelper implements Command {

	private static final Logger LOGGER = LogManager.getLogger( AddPlaceCoefficientCommand.class);
			
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String to = request.getParameter(JSPParameter.BOUND_TO);
		String value = request.getParameter(JSPParameter.VALUE);
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<PlaceCoefficient> coefficients = getPlaceCoefficientFromSession(session);
		try {
			getFlightCreatorService().addPlaceCoefficient(flight, coefficients, to, value);
			session.setAttribute(Attribute.PLACE_COEFFICIENT.toString(), coefficients);
			return Page.FLIGHT_PREVIEW.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			LOGGER.debug(e.getMessage());
			return new RedirectToAddPlaceCoefficientPageCommand().execute(request, response);
		}
	}
}