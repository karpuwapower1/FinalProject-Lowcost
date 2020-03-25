package by.training.karpilovich.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.InitializatorService;

@WebFilter(urlPatterns = { "/*" })
public class SigninFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SigninFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("filter inited");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.debug("inside filter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		Cookie[] cookies = httpRequest.getCookies();
		if (session.getAttribute(AttributeName.ROLE.getName()) == null) {
			LOGGER.debug("Inside if");
			User user = initializeUser(cookies, session);
			setSessionAttribute(session, user);
		}
		chain.doFilter(httpRequest, response);
	}

	private User initializeUser(Cookie[] cookies, HttpSession session) {
		User user = null;
		String email;
		String password;
		if (cookies != null && !(email = getCookieValue(cookies, CookieName.EMAIL.getName())).isEmpty()
				&& !(password = getCookieValue(cookies, CookieName.PASSWORD.getName())).isEmpty()) {
			ServiceFactory factory = ServiceFactory.getInstance();
			InitializatorService initializationService = factory.getInitializatorService();
			try {
				user = initializationService.signin(email, password);
			} catch (ServiceException e) {
				LOGGER.warn(e);
			}
		}
		return user;
	}

	private String getCookieValue(Cookie[] cookies, String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return new String();
	}

	private void setSessionAttribute(HttpSession session, User user) {
		if (user != null) {
			session.setAttribute(AttributeName.USER.getName(), user);
			session.setAttribute(AttributeName.ROLE.getName(), user.getRole());
			return;
		}
		session.setAttribute(AttributeName.ROLE.getName(), Role.GUEST);
	}
}
