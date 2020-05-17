package by.training.karpilovich.lowcost.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.impl.admin.ticket.ShowAllTicketCommand;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ReturnTicketCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = ((User) request.getSession().getAttribute(Attribute.USER.toString()));
		try {
			returnTicket(user, request);
			request.getSession().setAttribute(Attribute.USER.toString(), updateUser(user));
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return new ShowAllTicketCommand().execute(request, response);
	}

	private void returnTicket(User user, HttpServletRequest request) throws ServiceException {
		String ticketNumber = request.getParameter(JSPParameter.NUMBER);
		getTicketService().returnTicket(user, ticketNumber);
	}

	private User updateUser(User user) throws ServiceException {
		return getUserService().signIn(user.getEmail(), user.getPassword());
	}
}