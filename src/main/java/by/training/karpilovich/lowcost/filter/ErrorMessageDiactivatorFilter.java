package by.training.karpilovich.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import by.training.karpilovich.lowcost.command.Attribute;

@WebFilter(urlPatterns = { "/*" })
public class ErrorMessageDiactivatorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		deativateErrorMessage(httpRequest);
		chain.doFilter(httpRequest, response);
	}

	private void deativateErrorMessage(HttpServletRequest request) {
		request.getSession().setAttribute(Attribute.ERROR_MESSAGE.toString(), null);
	}
}