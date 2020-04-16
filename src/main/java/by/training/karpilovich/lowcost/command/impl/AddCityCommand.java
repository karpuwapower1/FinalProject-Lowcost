package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cityName = request.getParameter(JSPParameter.CITY_NAME);
		String countryName = request.getParameter(JSPParameter.COUNTRY);
		Page page = (Page) request.getSession().getAttribute(Attribute.PAGE_FROM.toString());
		try {
			getCityService().addCity(cityName, countryName);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return getReturnedPageAddress(page, request, response);
	}

}
