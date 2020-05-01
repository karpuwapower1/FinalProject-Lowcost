package by.training.karpilovich.lowcost.command.impl.admin.city;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class DeleteCityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter(JSPParameter.CITY_ID);
		try {
			getCityService().deleteCity(id);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return new ShowAllCitiesCommand().execute(request, response);
	}
}
