package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.Role;

public class SignoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		HttpSession newSession = request.getSession();
		newSession.setAttribute(AttributeName.ROLE.getName(), Role.GUEST);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			removeCookie(cookies, CookieName.EMAIL.getName(), response);
			removeCookie(cookies, CookieName.PASSWORD.getName(), response);
		}
		return Page.DEFAULT.getAddress();
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
