package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.AttributeName;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.Page;

public class RedirectCommand implements Command {

	private static final String TO_PAGE_REQUEST_PARAMETER = "to_page";
	private static final String FROM_PAGE_REQUEST_PARAMETER = "page_from";

	private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageTo = request.getParameter(TO_PAGE_REQUEST_PARAMETER);
		String pageFrom = request.getParameter(FROM_PAGE_REQUEST_PARAMETER);
		setAttribute(request, pageTo, pageFrom);
		Page page = takePage(pageTo);
		return page.getAddress();
	}
	
	private void setAttribute(HttpServletRequest request, String pageTo, String pageFrom) {
		HttpSession session = request.getSession();
		session.setAttribute(AttributeName.PAGE_TO.getName(), pageTo);
		if (pageFrom != null && !pageFrom.isEmpty()) {
			session.setAttribute(AttributeName.PAGE_FROM.getName(), Page.valueOf(pageFrom));
			LOGGER.debug(Page.valueOf(pageFrom));
		}
	}

	private Page takePage(String pageTo) {
		return (pageTo == null || pageTo.isEmpty()) ? Page.DEFAULT : Page.valueOf(pageTo.toUpperCase());
	}

}
