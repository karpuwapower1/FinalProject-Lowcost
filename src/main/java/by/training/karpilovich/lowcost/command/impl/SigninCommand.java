package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.InitializatorService;

public class SigninCommand implements Command {

	private static final String EMAIL_PARAMETER = "email";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String IS_REMEMBER = "memory";
	private static final int INACTIVE_TIMEOUT = 300;

	private static final Logger LOGGER = LogManager.getLogger(SigninCommand.class);

	@Override
	public String exequte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		HttpSession session = request.getSession();
		String email = request.getParameter(EMAIL_PARAMETER).trim();
		String password = request.getParameter(PASSWORD_PARAMETER).trim();

		try {
			User user = initializeUser(email, password);
			setAttribute(request, user);
			keepInMind(request, response, email, password);
			page = (Page) session.getAttribute(AttributeName.PAGE_FROM.getName());
			LOGGER.debug("Page = " + page.getAddress());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			LOGGER.warn(e);
		}
		return page != null ? page.getAddress() : Page.SIGNIN.getAddress();
	}

	private void keepInMind(HttpServletRequest request, HttpServletResponse response, String email, String password) {
		String isKeepInMind = request.getParameter(IS_REMEMBER);
		if (isKeepInMind != null) {
			setCookies(CookieName.EMAIL.getName(), request.getParameter(EMAIL_PARAMETER).trim(), response);
			setCookies(CookieName.PASSWORD.getName(), request.getParameter(PASSWORD_PARAMETER).trim(), response);
		} else {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(INACTIVE_TIMEOUT);
		}
	}

	private void setCookies(String name, String value, HttpServletResponse response) {
		response.addCookie(new Cookie(name, value));
	}

	private void setAttribute(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute(AttributeName.ROLE.getName(), user.getRole());
		session.setAttribute(AttributeName.USER.getName(), user);
	}

	private User initializeUser(String email, String password) throws ServiceException {
		ServiceFactory factory = ServiceFactory.getInstance();
		InitializatorService initializationService = factory.getInitializatorService();
		return initializationService.signin(email, password);
	}

}
