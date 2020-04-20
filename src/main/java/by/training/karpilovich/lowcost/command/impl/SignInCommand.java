package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.UserService;

public class SignInCommand implements Command {

	private static final int INACTIVE_TIMEOUT = 300;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		HttpSession session = request.getSession();
		String email = request.getParameter(JSPParameter.EMAIL);
		String password = request.getParameter(JSPParameter.PASSWORD);
		try {
			User user = initializeUser(email, password);
			setAttribute(request, user);
			keepInMind(request, response, email, password);
			page = (Page) session.getAttribute(Attribute.PAGE_FROM.toString());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.SIGN_IN;
		}
		return getReturnedPageAddress(page, request, response);
	}

	private void keepInMind(HttpServletRequest request, HttpServletResponse response, String email, String password) {
		String isKeepInMind = request.getParameter(JSPParameter.REMEMBER);
		if (isKeepInMind != null) {
			setCookies(CookieName.EMAIL.toString(), email, response);
			setCookies(CookieName.PASSWORD.toString(), password, response);
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
		session.setAttribute(Attribute.USER_ROLE.toString(), user.getRole());
		session.setAttribute(Attribute.USER.toString(), user);
	}

	private User initializeUser(String email, String password) throws ServiceException {
		UserService userService = getUserService();
		return userService.signIn(email, password);
	}

}