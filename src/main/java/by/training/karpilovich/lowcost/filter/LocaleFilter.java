package by.training.karpilovich.lowcost.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.LocaleType;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "locale", value = "EN") })
public class LocaleFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(LocaleFilter.class);

	private LocaleType defaultLocale;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		defaultLocale = LocaleType.valueOf(filterConfig.getInitParameter("locale"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		setLocale(httpRequest, httpResponse);
		chain.doFilter(httpRequest, httpResponse);
	}

	private void setLocale(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		LocaleType type;
		String locale;
		if (cookies != null && !(locale = findCookie(cookies, CookieName.LOCALE.getName())).isEmpty()) {
			type = LocaleType.valueOf(locale.toUpperCase());
		} else {
			type = defaultLocale;
			setCookie(response, type);
		}
		LOGGER.debug(type.getCountry() + " " + type.getLanguage());
		response.setLocale(new Locale(type.getLanguage(), type.getCountry()));
	}
	
	private void setCookie(HttpServletResponse response, LocaleType type) {
		Cookie cookie = new Cookie(CookieName.LOCALE.getName(), defaultLocale.toString());
		response.addCookie(cookie);
	}

	private String findCookie(Cookie[] cookies, String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return new String();
	}
}
