package by.training.karpilovich.lowcost.command.impl;

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
		String to = request.getParameter(JSPParameter.BOUND_TO);
		String from = request.getParameter(JSPParameter.BOUND_FROM);
		String value = request.getParameter(JSPParameter.VALUE);
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<DateCoefficient> coefficients = util.takeDateCoefficientFromSession(session);
		try {
			getDateCoefficientService().addDateCoefficientToSet(coefficients, flight.getDate(), from, to, value);
			session.setAttribute(Attribute.DATE_COEFFICIENT.toString(), coefficients);
			return Page.FLIGHT_PREVIEW.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToAddDateCoefficientPageCommand().execute(request, response);
		}
	}
}