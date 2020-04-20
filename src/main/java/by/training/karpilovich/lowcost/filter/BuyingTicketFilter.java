package by.training.karpilovich.lowcost.filter;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.CommandType;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.TicketService;

@WebFilter(urlPatterns = { "/*" })
public class BuyingTicketFilter implements Filter {

	private static final int COUNT_DOWN_LATCH_COUNT = 1;

	private static final Logger LOGGER = LogManager.getLogger(BuyingTicketFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String command = request.getParameter(JSPParameter.COMMAND);
		HttpSession session = httpRequest.getSession();
		AtomicBoolean flag = takeFlagFromSession(session);
		if (command != null && !command.isEmpty()) {
			CommandType commandType = CommandType.valueOf(request.getParameter(JSPParameter.COMMAND).toUpperCase());
			if (commandType == CommandType.BUY_TICKET || commandType == CommandType.CREATE_TICKET
					|| commandType == CommandType.REDIRECT_TO_CREATE_TICKET_PAGE) {
				if (flag.get()) {
					CountDownLatch countDownLatch = new CountDownLatch(COUNT_DOWN_LATCH_COUNT);
					session.setAttribute(Attribute.COUNT_DOWN_LATCH.toString(), countDownLatch);
					new Thread(new BuyTicketInterruptThread(session)).start();
				} else {
					unbookFlightPlaces(session);
					removeSessionAttributes(session);
					request.getRequestDispatcher(Page.DEFAULT.getAddress()).forward(httpRequest, httpResponse);
					return;
				}
			}
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	private AtomicBoolean takeFlagFromSession(HttpSession session) {
		AtomicBoolean flag = (AtomicBoolean) session.getAttribute(Attribute.BUYING_FLAG.toString());
		if (flag == null) {
			flag = new AtomicBoolean(true);
			session.setAttribute(Attribute.BUYING_FLAG.toString(), flag);
		}
		return flag;
	}

	private void unbookFlightPlaces(HttpSession session) {
		TicketService service = ServiceFactory.getInstance().getTicketService();
		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
		int quantity = (Integer) session.getAttribute(Attribute.PASSENGER_QUANTITY.toString());
		try {
			service.unbookTicketToFlight(flight, quantity);
		} catch (ServiceException e) {
			LOGGER.error("Error while unbboking places. Flight =" + flight + " quantity=" + quantity, e);
		}
	}

	private void removeSessionAttributes(HttpSession session) {
		session.removeAttribute(Attribute.BUYING_FLAG.toString());
		session.removeAttribute(Attribute.COUNT_DOWN_LATCH.toString());
		session.removeAttribute(Attribute.TICKETS.toString());
	}

	private class BuyTicketInterruptThread implements Runnable {

		private static final int INACTIVE_TIME_IN_MINUTES = 10;

		private CountDownLatch countDownlatch;
		AtomicBoolean flag;

		private BuyTicketInterruptThread(HttpSession session) {
			countDownlatch = (CountDownLatch) session.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
			flag = (AtomicBoolean) session.getAttribute(Attribute.BUYING_FLAG.toString());
		}

		@Override
		public void run() {
			boolean await = false;
			try {
				await = countDownlatch.await(INACTIVE_TIME_IN_MINUTES, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				flag.set(await);
				LOGGER.debug(flag);
			}
		}
	}
}