package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToCreateFlightPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		SortedSet<City> cities = getCityService().getAllCities();
		List<Plane> planes = getPlaneService().getAllPlanes();
		request.setAttribute(Attribute.CITIES.toString(), cities);
		request.setAttribute(Attribute.PLANES.toString(), planes);
		return Page.CREATE_FLIGHT.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToDefaultPageCommand().execute(request, response);
		}
	}
}
