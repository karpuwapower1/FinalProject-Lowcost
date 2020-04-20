package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class BuyTicketCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(BuyTicketCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Ticket> tickets = getTicketsFromSession(session);
		User user = (User) session.getAttribute(Attribute.USER.toString());
		CountDownLatch countDownLatch = (CountDownLatch) session.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		countDownLatch.countDown();
		try {
			List<Ticket> boughtTickets = getTicketService().byeTickets(user, tickets);
			session.setAttribute(Attribute.TICKETS.toString(), boughtTickets);
			return Page.SHOW_TICKET.getAddress();
		} catch (ServiceException e) {
			LOGGER.debug(e);
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return new RedirectToDefaultPageCommand().execute(request, response);
		}
	}
	
	private List<Ticket> getTicketsFromSession(HttpSession session) {
		List<Ticket> tickets = new ArrayList<>();
		List<?> objects = (List<?>) session.getAttribute(Attribute.TICKETS.toString());
		if (objects != null) {
			for (Object object : objects) {
				tickets.add((Ticket) object);
			}
		}
		return tickets;
	}
}