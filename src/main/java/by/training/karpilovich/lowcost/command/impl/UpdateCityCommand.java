package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JspParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class UpdateCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(JspParameter.CITY_NAME.toString());
		String id = request.getParameter(JspParameter.CITY_ID.toString());
		String country = request.getParameter(JspParameter.COUNTRY_NAME.toString());
		CityService service = getCityService();
		String address;
		try {
			service.updateCity(id, name, country);
			address = new ShowAllCitiesCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = Page.UPDATE_CITY.getAddress();
		}
		return address;
	}
}