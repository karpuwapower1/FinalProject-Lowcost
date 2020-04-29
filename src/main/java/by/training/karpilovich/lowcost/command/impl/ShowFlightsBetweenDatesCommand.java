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
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowFlightsBetweenDatesCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String from = request.getParameter(JSPParameter.BOUND_FROM);
		String to = request.getParameter(JSPParameter.BOUND_TO);
		try {
			List<Flight> flights = getFlightService().getFlightsBetweenDates(from, to);
			request.setAttribute(Attribute.FLIGHTS.toString(), flights);
			return Page.SHOW_FLIGHTS.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.CHOOSE_DATE_BOUNDS.getAddress();
		}
	}
}