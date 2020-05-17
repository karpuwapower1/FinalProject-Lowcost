package by.training.karpilovich.lowcost.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class DeleteUserCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			deleteUser(request);
			removeCookies(request.getCookies(), response);
			request.getSession().invalidate();
			createNewSession(request);
			page = Page.DEFAULT;
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.DELETE_USER;
		}
		return page.getAddress();
	}

	private void deleteUser(HttpServletRequest request) throws ServiceException {
		String repeatPassword = request.getParameter(JSPParameter.REPEAT_PASSWORD);
		User user = (User) request.getSession().getAttribute(Attribute.USER.toString());
		getUserService().deleteUser(user, repeatPassword);
	}

	private void removeCookies(Cookie[] cookies, HttpServletResponse response) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	private void createNewSession(HttpServletRequest request) throws ServiceException {
		HttpSession newSession = request.getSession();
		newSession.setAttribute(Attribute.USER_ROLE.toString(), Role.GUEST);
		newSession.setAttribute(Attribute.CITIES.toString(), getCityService().getAllCities());
	}
}