package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.service.UserService;

public class DeleteUserCommand implements Command {
	
	private static final String REPEAT_PASSWORD_PARAMETER_NAME = "repeatPassword";

	private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String repeatPassword = request.getParameter(REPEAT_PASSWORD_PARAMETER_NAME);
		User user = (User) session.getAttribute(Attribute.USER.toString());
		Page page = null;
		try {
			deleteUser(user, repeatPassword);
			session.invalidate();
			HttpSession newSession = request.getSession();
			newSession.setAttribute(Attribute.ROLE.toString(), Role.GUEST);
			page = Page.DEFAULT;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = (Page) session.getAttribute(Attribute.PAGE_FROM.toString());
			LOGGER.warn(e);
		}
		return page == null ? new RedirectToDefaultPageCommand().execute(request, response) : page.getAddress();
	}

	private void deleteUser(User user, String repeatPassword) throws ServiceException {
		UserService userService = getUserService();
		userService.delete(user, repeatPassword);
	}
}