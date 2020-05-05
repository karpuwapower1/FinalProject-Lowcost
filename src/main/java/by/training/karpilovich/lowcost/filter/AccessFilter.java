package by.training.karpilovich.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import by.training.karpilovich.lowcost.command.Page;

@WebFilter(urlPatterns = "*.jsp")
public class AccessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		request.getRequestDispatcher(httpRequest.getContextPath() + Page.DEFAULT.getAddress()).forward(httpRequest, response);
	}
}