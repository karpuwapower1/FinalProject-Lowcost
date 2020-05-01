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
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.entity.Role;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class DeleteUserCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String repeatPassword = request.getParameter(JSPParameter.REPEAT_PASSWORD);
		User user = (User) session.getAttribute(Attribute.USER.toString());
		try {
			getUserService().deleteUser(user, repeatPassword);
			removeCookies(request.getCookies(), response);
			session.invalidate();
			HttpSession newSession = request.getSession();
			newSession.setAttribute(Attribute.USER_ROLE.toString(), Role.GUEST);
			return new RedirectToDefaultPageCommand().execute(request, response);
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.DELETE_USER.getAddress();
		}
	}

	private void removeCookies(Cookie[] cookies, HttpServletResponse response) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}
}