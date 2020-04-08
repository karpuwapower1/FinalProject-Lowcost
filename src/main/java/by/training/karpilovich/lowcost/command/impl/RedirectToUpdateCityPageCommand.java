package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JspParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.CityService;

public class RedirectToUpdateCityPageCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(RedirectToUpdateCityPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CityService service = getCityService();
		HttpSession session = request.getSession();
		String id = request.getParameter(JspParameter.CITY_ID.toString());
		Page page;
		try {
			City city = service.getCityById(id);
			session.setAttribute(Attribute.CITY.toString(), city);
			page = Page.UPDATE_CITY;
		} catch (ServiceException e) {
			LOGGER.debug(e);
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.ALL_CITIES;
		}
		return page.getAddress();
	}
}
