package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

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
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateFlightPageCommand;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class CreateFlightCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String number = request.getParameter(JSPParameter.NUMBER).trim();
		String from = request.getParameter(JSPParameter.COUNTRY_FROM).trim();
		String to = request.getParameter(JSPParameter.COUNTRY_TO).trim();
		String plane = request.getParameter(JSPParameter.PLANE).trim();
		String date = request.getParameter(JSPParameter.DATE).trim();
		String price = request.getParameter(JSPParameter.PRICE).trim();
		String luggage = request.getParameter(JSPParameter.LUGGAGE).trim();
		String priceForKgOverweigth = request.getParameter(JSPParameter.OVERWEIGHT_PRICE).trim();
		String primaryBoardingPrice = request.getParameter(JSPParameter.PRIMARY_BOARDING_PRICE).trim();
		HttpSession session = request.getSession();
		try {
			Flight flight = getFlightCreatorService().createFlight(number, from, to, date, price, primaryBoardingPrice,
					plane, luggage, priceForKgOverweigth);
			session.setAttribute(Attribute.FLIGHT.toString(), flight);
			return Page.FLIGHT_PREVIEW.getAddress();
		} catch (ServiceException e) {
			LOGGER.debug(e.getMessage(), e);
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToCreateFlightPageCommand().execute(request, response);
		}
	}
}