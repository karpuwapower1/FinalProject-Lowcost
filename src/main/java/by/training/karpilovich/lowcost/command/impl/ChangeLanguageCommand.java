package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.LocaleType;
import by.training.karpilovich.lowcost.command.Page;

public class ChangeLanguageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Locale locale = getLocaleFromRequest(request);
		setCookie(response, locale);
		setLocale(request, response, locale);
		return Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE)).getAddress();
	}

	private Locale getLocaleFromRequest(HttpServletRequest request) {
		String language = request.getParameter(JSPParameter.LANGUAGE);
		LocaleType localeType = LocaleType.valueOf(language);
		return new Locale(localeType.getLanguage(), localeType.getCountry());
	}

	private void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		HttpSession session = request.getSession();
		response.setLocale(locale);
		session.setAttribute(Attribute.LOCALE.toString(), locale);
	}

	private void setCookie(HttpServletResponse response, Locale locale) {
		Cookie cookie = new Cookie(CookieName.LOCALE.toString(), locale.getLanguage());
		response.addCookie(cookie);
	}
}