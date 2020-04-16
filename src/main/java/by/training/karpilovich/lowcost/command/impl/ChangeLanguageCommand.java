package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.LocaleType;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToDefaultPageCommand;

public class ChangeLanguageCommand implements Command {
	
	private static final Logger LOGGER = LogManager.getLogger(ChangeLanguageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String language = request.getParameter(JSPParameter.LANGUAGE);
		LOGGER.debug(language);
		LocaleType localeType = LocaleType.valueOf(language);
		setCookie(response, localeType);
		response.setLocale(new Locale(localeType.getLanguage(), localeType.getCountry()));
		LOGGER.debug(response.getLocale().getLanguage() + " " + response.getLocale().getCountry());
		Page page = Page.valueOf(request.getParameter(JSPParameter.FROM_PAGE));
		return page == Page.DEFAULT ? new RedirectToDefaultPageCommand().execute(request, response) : page.getAddress();
	}

	private void setCookie(HttpServletResponse response, LocaleType type) {
		Cookie cookie = new Cookie(CookieName.LOCALE.toString(), type.toString());
		response.addCookie(cookie);
	}
}