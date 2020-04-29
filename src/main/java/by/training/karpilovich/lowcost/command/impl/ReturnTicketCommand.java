package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ReturnTicketCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ticketNumber = request.getParameter(JSPParameter.NUMBER);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER.toString());
		try {
			getTicketService().returnTicket(user, ticketNumber);
			user = getUserService().signIn(user.getEmail(), user.getPassword());
			session.setAttribute(Attribute.USER.toString(), user);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return new ShowAllTicketCommand().execute(request, response);
	}
}