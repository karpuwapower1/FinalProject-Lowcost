package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		String cityId = request.getParameter(JSPParameter.CITY_ID);
		try {
			City city = getCityService().getCityById(cityId);
			session.setAttribute(Attribute.CITY.toString(), city);
			return Page.UPDATE_CITY.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.ALL_CITIES.getAddress();
		}
	}
}