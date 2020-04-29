package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToUpdateFlightPage;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class UpdateFlightCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flightId = request.getParameter(JSPParameter.FLIGHT_ID);
		String number = request.getParameter(JSPParameter.NUMBER).trim();
		String from = request.getParameter(JSPParameter.COUNTRY_FROM).trim();
		String to = request.getParameter(JSPParameter.COUNTRY_TO).trim();
		String plane = request.getParameter(JSPParameter.PLANE).trim();
		String date = request.getParameter(JSPParameter.DATE).trim();
		String price = request.getParameter(JSPParameter.PRICE).trim();
		String luggage = request.getParameter(JSPParameter.LUGGAGE).trim();
		String priceForKgOverweigth = request.getParameter(JSPParameter.OVERWEIGHT_PRICE).trim();
		String primaryBoardingPrice = request.getParameter(JSPParameter.PRIMARY_BOARDING_PRICE).trim();
		try {
			City cityFrom = getCityService().getCityById(from);
			City cityTo = getCityService().getCityById(to);
			Plane planeModel = getPlaneService().getPlaneByModel(plane);
			getFlightService().updateFlight(flightId, number, cityFrom, cityTo, date, price, primaryBoardingPrice,
					planeModel, luggage, priceForKgOverweigth);
			return new ShowAllFlightsCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToUpdateFlightPage().execute(request, response);
		}
	}
}