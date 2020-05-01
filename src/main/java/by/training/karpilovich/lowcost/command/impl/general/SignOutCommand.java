package by.training.karpilovich.lowcost.command.impl.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.entity.Role;

public class SignOutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		deleteSession(request);
		clearCookies(request.getCookies(), response);
		createNewSession(request);
		return new RedirectToDefaultPageCommand().execute(request, response);
	}

	private void deleteSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}

	private void createNewSession(HttpServletRequest request) {
		HttpSession newSession = request.getSession();
		newSession.setAttribute(Attribute.USER_ROLE.toString(), Role.GUEST);
	}

	private void clearCookies(Cookie[] cookies, HttpServletResponse response) {
		if (cookies != null) {
			removeCookie(cookies, CookieName.EMAIL.toString(), response);
			removeCookie(cookies, CookieName.PASSWORD.toString(), response);
		}
	}

	private void removeCookie(Cookie[] cookies, String name, HttpServletResponse response) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}
}