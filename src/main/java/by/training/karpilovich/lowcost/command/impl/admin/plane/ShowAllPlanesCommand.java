package by.training.karpilovich.lowcost.command.impl.admin.plane;

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
		Page page = null;
		try {
			setPlanesToRequest(request, getAllPlanes());
			page = Page.SHOW_PLANES;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE.toUpperCase()));
		}
		return page.getAddress();
	}
	
	private void setPlanesToRequest(HttpServletRequest request, List<Plane> planes) {
		request.setAttribute(Attribute.PLANES.toString(), planes);
	}
	
	private List<Plane> getAllPlanes() throws ServiceException {
		return getPlaneService().getAllPlanes();
	}
}