package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;
import java.util.Calendar;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RedirectToAddDateCoefficientPageCommand implements Command {

	private final AttributeReceiverUtil util = new AttributeReceiverUtil();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			setAttributes(request);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return Page.ADD_DATE_COEFFICIENT.getAddress();
	}

	private void setAttributes(HttpServletRequest request) throws ServiceException {
		HttpSession session = request.getSession();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		SortedSet<DateCoefficient> coefficinents = util.takeDateCoefficientFromSession(session);
		Calendar boundFrom = getDateCoefficientService().getNextBoundFromValueDateCoefficient(coefficinents);
		request.setAttribute(Attribute.MAX_BOUND_VALUE.toString(), flight.getDate());
		request.setAttribute(Attribute.BOUND_FROM.toString(), boundFrom);
	}
}