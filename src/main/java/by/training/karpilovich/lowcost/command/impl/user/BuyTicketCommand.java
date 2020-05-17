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
		countDown(request);
		User user = getUser(request);
		Page page = null;
		try {
			request.setAttribute(Attribute.TICKETS.toString(), buyTicket(user, request));
			updateUserIntoSession(request.getSession(), getUpdatedUser(user));
			page = Page.SHOW_TICKET;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.DEFAULT;
		} finally {
			removeAttributes(request);
		}
		return page.getAddress();
	}

	private void countDown(HttpServletRequest request) {
		CountDownLatch countDownLatch = (CountDownLatch) request.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		countDownLatch.countDown();
	}

	private List<Ticket> buyTicket(User user, HttpServletRequest request) throws ServiceException {
		return getTicketService().byeTickets(user, util.takeTicketsFromSession(request.getSession()));
	}

	private User getUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(Attribute.USER.toString());
	}

	private User getUpdatedUser(User user) throws ServiceException {
		return getUserService().signIn(user.getEmail(), user.getPassword());
	}

	private void updateUserIntoSession(HttpSession session, User user) {
		session.setAttribute(Attribute.USER.toString(), user);
	}

	private void removeAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Attribute.TICKETS.toString());
		session.removeAttribute(Attribute.PASSENGER_QUANTITY.toString());
		session.removeAttribute(Attribute.FLIGHT.toString());
		session.removeAttribute(Attribute.COUNT_DOWN_LATCH.toString());
	}
}