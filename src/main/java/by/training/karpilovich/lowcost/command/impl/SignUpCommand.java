package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.UserService;

public class SignUpCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Page page = null;
		try {
			User user = signupUser(request);
			setAttribute(session, user);
			page = (Page) session.getAttribute(Attribute.PAGE_FROM.toString());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.SIGN_UP;
		}
		return page == null || page == Page.DEFAULT ? new RedirectToDefaultPageCommand().execute(request, response)
				: page.getAddress();
	}

	private User signupUser(HttpServletRequest request) throws ServiceException {
		String email = request.getParameter(JSPParameter.EMAIL);
		String password = request.getParameter(JSPParameter.PASSWORD);
		String repeatedPassword = request.getParameter(JSPParameter.REPEAT_PASSWORD);
		String firstName = request.getParameter(JSPParameter.FIRST_NAME);
		String lastName = request.getParameter(JSPParameter.LAST_NAME);
		UserService userService = getUserService();
		return userService.signUp(email, password, repeatedPassword, firstName, lastName);
	}

	private void setAttribute(HttpSession session, User user) {
		session.setAttribute(Attribute.ROLE.toString(), user.getRole());
		session.setAttribute(Attribute.USER.toString(), user);
	}
}