package by.training.karpilovich.lowcost.command.impl.admin.plane;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddPlaneCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	String address = null;
		try {
			addPlane(request);
			address = new ShowAllPlanesCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			address = Page.ADD_PLANE.getAddress();
		}
		return address;
	}
	
	private void addPlane(HttpServletRequest request) throws ServiceException {
		String model = request.getParameter(JSPParameter.PLANE_MODEL);
		String placeQuantity = request.getParameter(JSPParameter.QUANTITY);
		getPlaneService().add(model, placeQuantity);
	}
}
