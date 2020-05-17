package by.training.karpilovich.lowcost.command.impl.admin.flight;

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
		String address = null;
		try {
			upateFlight(request);
			address = new ShowAllFlightsCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = new RedirectToUpdateFlightPage().execute(request, response);
		}
		return address;
	}

	private void upateFlight(HttpServletRequest request) throws ServiceException {
		String flightId = request.getParameter(JSPParameter.FLIGHT_ID);
		String number = request.getParameter(JSPParameter.NUMBER);
		String date = request.getParameter(JSPParameter.DATE);
		String price = request.getParameter(JSPParameter.PRICE);
		String luggage = request.getParameter(JSPParameter.LUGGAGE);
		String priceForKgOverweigth = request.getParameter(JSPParameter.OVERWEIGHT_PRICE);
		String primaryBoardingPrice = request.getParameter(JSPParameter.PRIMARY_BOARDING_PRICE);
		City cityFrom = getCityService().getCityById(request.getParameter(JSPParameter.COUNTRY_FROM));
		City cityTo = getCityService().getCityById(request.getParameter(JSPParameter.COUNTRY_TO));
		Plane planeModel = getPlaneService().getPlaneByModel(request.getParameter(JSPParameter.PLANE));
		getFlightService().updateFlight(flightId, number, cityFrom, cityTo, date, price, primaryBoardingPrice,
				planeModel, luggage, priceForKgOverweigth);
	}
}