package by.training.karpilovich.lowcost.command.impl.user;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.util.AttributeReceiverUtil;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class BuyTicketCommand implements Command {
	
	private final AttributeReceiverUtil util = new AttributeReceiverUtil();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Ticket> tickets = util.takeTicketsFromSession(session);
		User user = (User) session.getAttribute(Attribute.USER.toString());
		CountDownLatch countDownLatch = (CountDownLatch) session.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		countDownLatch.countDown();
		try {
			List<Ticket> boughtTickets = getTicketService().byeTickets(user, tickets);
			request.setAttribute(Attribute.TICKETS.toString(), boughtTickets);
			user = getUserService().signIn(user.getEmail(), user.getPassword());
			session.setAttribute(Attribute.USER.toString(), user);
			return Page.SHOW_TICKET.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.DEFAULT.getAddress();
		} finally {
			removeSessionAttribute(session);
		}
	}
	
	private void removeSessionAttribute(HttpSession session) {
		session.removeAttribute(Attribute.TICKETS.toString());
		session.removeAttribute(Attribute.PASSENGER_QUANTITY.toString());
		session.removeAttribute(Attribute.FLIGHT.toString());
		session.removeAttribute(Attribute.COUNT_DOWN_LATCH.toString());
	}
}