package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToUpdateCityPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			request.getSession().setAttribute(Attribute.CITY.toString(),
					getCity(request.getParameter(JSPParameter.CITY_ID)));
			page = Page.UPDATE_CITY;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.ALL_CITIES;
		}
		return page.getAddress();
	}

	private City getCity(String id) throws ServiceException {
		return getCityService().getCityById(id);
	}
}