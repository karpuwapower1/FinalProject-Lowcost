package by.training.karpilovich.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.UserService;

@WebFilter(urlPatterns = { "/*" })
public class SignInFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SignInFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute(Attribute.USER_ROLE.toString()) == null) {
			User user = getUser(httpRequest);
			setSessionAttribute(session, user);
		}
		chain.doFilter(httpRequest, response);
	}

	private User getUser(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		return cookies == null ? null : initializeUser(cookies);
	}

	private User initializeUser(Cookie[] cookies) {
		String email = getCookieValue(cookies, CookieName.EMAIL.toString());
		String password = getCookieValue(cookies, CookieName.PASSWORD.toString());
		UserService userService = ServiceFactory.getInstance().getUserService();
		try {
			return userService.signIn(email, password);
		} catch (ServiceException e) {
			return null;
		}
	}

	private String getCookieValue(Cookie[] cookies, String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	private void setSessionAttribute(HttpSession session, User user) {
		if (user != null) {
			session.setAttribute(Attribute.USER.toString(), user);
			LOGGER.debug(user.getBalanceAmount());
			session.setAttribute(Attribute.USER_ROLE.toString(), user.getRole());
			return;
		}
		session.setAttribute(Attribute.USER_ROLE.toString(), Role.GUEST);
	}
}