package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToAddDateCoefficientPageCommand;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class AddDateCoefficientCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		SortedSet<DateCoefficient> coefficients = util.takeDateCoefficientFromSession(session);
		try {
			addCoefficientToSet(request, coefficients);
			session.setAttribute(Attribute.DATE_COEFFICIENT.toString(), coefficients);
			return Page.FLIGHT_PREVIEW.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToAddDateCoefficientPageCommand().execute(request, response);
		}
	}

	private void addCoefficientToSet(HttpServletRequest request, SortedSet<DateCoefficient> coefficients)
			throws ServiceException {
		getDateCoefficientService().addDateCoefficientToSet(coefficients, createDateCoeffient(request));
	}

	private DateCoefficient createDateCoeffient(HttpServletRequest request) throws ServiceException {
		String to = request.getParameter(JSPParameter.BOUND_TO);
		String from = request.getParameter(JSPParameter.BOUND_FROM);
		String value = request.getParameter(JSPParameter.VALUE);
		Flight flight = (Flight) request.getSession().getAttribute(Attribute.FLIGHT.toString());
		return getDateCoefficientService().createDateCoefficient(flight.getDate(), from, to, value);
	}
}