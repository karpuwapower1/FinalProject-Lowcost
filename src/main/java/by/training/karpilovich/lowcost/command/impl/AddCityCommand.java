package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class AddCityCommand implements Command {

	private static final String CITY_NAME_PARAMETER = "city";
	private static final String COUNTRY_NAME_PARAMETER = "country";

	private static final Logger LOGGER = LogManager.getLogger(AddCityCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cityName = request.getParameter(CITY_NAME_PARAMETER);
		String countryName = request.getParameter(COUNTRY_NAME_PARAMETER);
		LOGGER.debug(cityName + " " + countryName);
		Page page = (Page) request.getSession().getAttribute(AttributeName.PAGE_FROM.getName());
		CityService service = getCityService();
		try {
			service.addCity(cityName, countryName);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return page == null || page == Page.DEFAULT ? new RedirectToDefaultPageCommand().execute(request, response)
				: page.getAddress();
	}

}
