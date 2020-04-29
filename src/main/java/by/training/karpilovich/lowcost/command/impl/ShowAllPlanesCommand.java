package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowAllPlanesCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Plane> planes = getPlaneService().getAllPlanes();
			request.setAttribute(Attribute.PLANES.toString(), planes);
			return Page.SHOW_PLANES.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE.toUpperCase())).getAddress();
		}
	}
}