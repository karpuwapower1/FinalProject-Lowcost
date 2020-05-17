package by.training.karpilovich.lowcost.command.impl.admin.ticket;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ShowAllTicketCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			setTicketsToRequest(request,
					getAllTicketBelongsToUser((User) request.getSession().getAttribute(Attribute.USER.toString())));
			page = Page.SHOW_TICKET;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE.toUpperCase()));
		}
		return page.getAddress();
	}

	private void setTicketsToRequest(HttpServletRequest request, List<Ticket> tickets) {
		request.setAttribute(Attribute.TICKETS.toString(), tickets);
	}

	private List<Ticket> getAllTicketBelongsToUser(User user) throws ServiceException {
		return getTicketService().getAllUserTickets(user);
	}
}