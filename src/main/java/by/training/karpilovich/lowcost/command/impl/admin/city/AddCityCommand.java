package by.training.karpilovich.lowcost.command.impl.admin.city;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			addCity(request);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return new ShowAllCitiesCommand().execute(request, response);
	}

	private void addCity(HttpServletRequest request) throws ServiceException {
		String cityName = request.getParameter(JSPParameter.CITY_NAME);
		String countryName = request.getParameter(JSPParameter.COUNTRY);
		getCityService().addCity(cityName, countryName);
	}
}