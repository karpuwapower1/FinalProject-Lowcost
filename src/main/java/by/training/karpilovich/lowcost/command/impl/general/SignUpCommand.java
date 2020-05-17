package by.training.karpilovich.lowcost.command.impl.general;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.UserService;

public class SignUpCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			User user = signUpUser(request);
			setAttribute(request, user);
			sendGreetingEmail(user, response.getLocale());
			page = (Page) request.getSession().getAttribute(Attribute.PAGE_FROM.toString());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.SIGN_UP;
		}
		return page.getAddress();
	}

	private User signUpUser(HttpServletRequest request) throws ServiceException {
		String email = request.getParameter(JSPParameter.EMAIL);
		String password = request.getParameter(JSPParameter.PASSWORD);
		String repeatedPassword = request.getParameter(JSPParameter.REPEAT_PASSWORD);
		String firstName = request.getParameter(JSPParameter.FIRST_NAME);
		String lastName = request.getParameter(JSPParameter.LAST_NAME);
		UserService userService = getUserService();
		return userService.signUp(email, password, repeatedPassword, firstName, lastName);
	}

	private void setAttribute(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute(Attribute.USER_ROLE.toString(), user.getRole());
		session.setAttribute(Attribute.USER.toString(), user);
	}

	private void sendGreetingEmail(User user, Locale locale) {
		getEmailSenderService().sendGreetingMessage(user.getEmail(), user.getFirstName(), user.getLastName(), locale);
	}
}