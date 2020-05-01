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

public class RedirectToDefaultPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = Page.DEFAULT;
		if (session.getAttribute(Attribute.CITIES.toString()) == null) {
			try {
				Set<City> cities = getCityService().getAllCities();
				session.setAttribute(Attribute.CITIES.toString(), cities);
			} catch (ServiceException e) {
				page = Page.INTERNAL_ERROR;
			}
		}
		return page.getAddress();
	}
}