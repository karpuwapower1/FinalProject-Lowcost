package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.UserService;

public class SignUpCommand implements Command {

	private static final String EMAIL_PARAMETER_NAME = "email";
	private static final String PASSWORD_PARAMETER_NAME = "password";
	private static final String REPEAT_PASSWORD_PARAMETER_NAME = "repeatPassword";
	private static final String FIRST_NAME_PARAMETER_NAME = "firstName";
	private static final String LAST_NAME_PARAMETER_NAME = "lastName";

	private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = null;
		try {
			User user = signupUser(request);
			setAttribute(session, user);
			page = (Page) session.getAttribute(AttributeName.PAGE_FROM.getName());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.SIGN_UP;
			LOGGER.warn(e);
		}
		return page == null || page == Page.DEFAULT ? new RedirectToDefaultPageCommand().execute(request, response)
				: page.getAddress();
	}

	private User signupUser(HttpServletRequest request) throws ServiceException {
		String email = request.getParameter(EMAIL_PARAMETER_NAME);
		String password = request.getParameter(PASSWORD_PARAMETER_NAME);
		String repeatedPassword = request.getParameter(REPEAT_PASSWORD_PARAMETER_NAME);
		String firstName = request.getParameter(FIRST_NAME_PARAMETER_NAME);
		String lastName = request.getParameter(LAST_NAME_PARAMETER_NAME);
		UserService userService = getUserService();
		return userService.signUp(email, password, repeatedPassword, firstName, lastName);
	}

	private void setAttribute(HttpSession session, User user) {
		session.setAttribute(AttributeName.ROLE.getName(), user.getRole());
		session.setAttribute(AttributeName.USER.getName(), user);
	}
}
