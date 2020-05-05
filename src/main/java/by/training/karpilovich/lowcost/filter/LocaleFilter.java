package by.training.karpilovich.lowcost.filter;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

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
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.CookieName;
import by.training.karpilovich.lowcost.command.LocaleType;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "locale", value = "EN") })
public class LocaleFilter implements Filter {

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
		HttpSession session = request.getSession();
		Locale locale = getLocale(request);
		if (locale == null) {
			LocaleType type = getLocaleType(request);
			locale = new Locale(type.getLanguage(), type.getCountry());
			setLocaleCookie(type, response);
			session.setAttribute(Attribute.LOCALE.toString(), locale);
		}
		response.setLocale(locale);
	}

	private Locale getLocale(HttpServletRequest request) {
		return (Locale) request.getSession().getAttribute(Attribute.LOCALE.toString());
	}

	private LocaleType getLocaleType(HttpServletRequest request) {
		Optional<String> optional = getLocaleFromCookies(request);
		return optional.isPresent() ? LocaleType.valueOf(optional.get().toUpperCase()) : defaultLocale;
	}

	private Optional<String> getLocaleFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			return findCookie(cookies, CookieName.LOCALE.toString());
		}
		return Optional.empty();
	}

	private Optional<String> findCookie(Cookie[] cookies, String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
				return Optional.of(cookie.getValue());
			}
		}
		return Optional.empty();
	}

	private void setLocaleCookie(LocaleType type, HttpServletResponse response) {
		if (type == defaultLocale) {
			setCookie(response);
		}
	}

	private void setCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(CookieName.LOCALE.toString(), defaultLocale.toString());
		response.addCookie(cookie);
	}
}