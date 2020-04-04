package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class AddCityCommand implements Command {
	
	private static final String CITY_NAME_PARAMETER = "city";
	private static final String COUNTRY_NAME_PARAMETER = "country";
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cityName = request.getParameter(CITY_NAME_PARAMETER);
		String countryName = request.getParameter(COUNTRY_NAME_PARAMETER);
		CityService service = getCityService();
		
		Page page = (Page) request.getSession().getAttribute(AttributeName.PAGE_FROM.getName());
		try {
			service.addCity(cityName, countryName);
			
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return page == null ? new RedirectToDefaultPageCommand().execute(request, response) : page.getAddress();
	}
	
	

}
