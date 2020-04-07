package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class RedirectToDefaultPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CityService cityService = getCityService();
		try {
			List<City> cities = cityService.getAllCities();
			request.setAttribute(Attribute.CITIES.toString(), cities);
			return Page.DEFAULT.getAddress();
		} catch (ServiceException e) {
			return Page.INTERNAL_ERROR.getAddress();
		}
	}

}
