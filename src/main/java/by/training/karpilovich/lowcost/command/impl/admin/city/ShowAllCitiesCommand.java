package by.training.karpilovich.lowcost.command.impl.admin.city;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowAllCitiesCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			setCitiesToRequest(request, getAllCities());
			page = Page.ALL_CITIES;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = (Page) request.getSession().getAttribute(Attribute.PAGE_FROM.toString().toUpperCase());
		}
		return page.getAddress();
	}

	private void setCitiesToRequest(HttpServletRequest request, Set<City> cities) {
		request.setAttribute(Attribute.CITIES.toString(), cities);
	}

	private Set<City> getAllCities() throws ServiceException {
		return getCityService().getAllCities();
	}
}