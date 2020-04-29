package by.training.karpilovich.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;


@WebFilter(urlPatterns = { "/*" })
public class ErrorMessageDiactivatorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		session.setAttribute(Attribute.ERROR_MESSAGE.toString(), null);
		chain.doFilter(httpRequest, response);
	}
}