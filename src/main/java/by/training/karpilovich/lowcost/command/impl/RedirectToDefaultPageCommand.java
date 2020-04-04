package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class RedirectToDefaultPageCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(RedirectToDefaultPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CityService cityService = getCityService();
		try {
			List<City> cities = cityService.getAllCities();
			request.setAttribute(AttributeName.CITIES.getName(), cities);
			return Page.DEFAULT.getAddress();
		} catch (ServiceException e) {
			return Page.INTERNAL_ERROR.getAddress();
		}
	}

}
