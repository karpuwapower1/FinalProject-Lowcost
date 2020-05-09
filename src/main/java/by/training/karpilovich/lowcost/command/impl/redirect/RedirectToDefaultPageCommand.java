package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;

public class RedirectToDefaultPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			setCitiesToSession(request.getSession());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return Page.DEFAULT.getAddress();
	}

	private void setCitiesToSession(HttpSession session) throws ServiceException {
		if (session.getAttribute(Attribute.CITIES.toString()) == null) {
			session.setAttribute(Attribute.CITIES.toString(), getCities());
		}
	}

	private Set<City> getCities() throws ServiceException {
		CityService cityService = ServiceFactory.getInstance().getCityService();
		return cityService.getAllCities();
	}
}