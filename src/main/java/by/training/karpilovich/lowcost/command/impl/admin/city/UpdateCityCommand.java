package by.training.karpilovich.lowcost.command.impl.admin.city;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class UpdateCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String address;
		try {
			updateCity(request);
			address = new ShowAllCitiesCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = Page.UPDATE_CITY.getAddress();
		}
		return address;
	}

	private void updateCity(HttpServletRequest request) throws ServiceException {
		String name = request.getParameter(JSPParameter.CITY_NAME);
		String id = request.getParameter(JSPParameter.CITY_ID);
		String country = request.getParameter(JSPParameter.COUNTRY);
		getCityService().updateCity(id, name, country);
	}
}