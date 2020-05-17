package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToUpdateFlightPage implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			request.setAttribute(Attribute.FLIGHT.toString(), getFlight(request.getParameter(JSPParameter.FLIGHT_ID)));
			page = Page.UPDATE_FLIGHT;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.SHOW_FLIGHTS;
		}
		return page.getAddress();
	}

	private Flight getFlight(String id) throws ServiceException {
		return getFlightService().getFlightById(id);
	}
}