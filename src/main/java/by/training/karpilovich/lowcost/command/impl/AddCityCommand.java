package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JspParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class AddCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cityName = request.getParameter(JspParameter.CITY_NAME.toString());
		String countryName = request.getParameter(JspParameter.COUNTRY_NAME.toString());
		Page page = (Page) request.getSession().getAttribute(Attribute.PAGE_FROM.toString());
		CityService service = getCityService();
		try {
			service.addCity(cityName, countryName);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return getReturnedPageAddress(page, request, response);
	}

}
