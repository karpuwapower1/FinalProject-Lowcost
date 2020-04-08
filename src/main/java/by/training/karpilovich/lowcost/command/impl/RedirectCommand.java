package by.training.karpilovich.lowcost.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JspParameter;
import by.training.karpilovich.lowcost.command.Page;

public class RedirectCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(RedirectCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageTo = request.getParameter(JspParameter.TO_PAGE.toString());
		String pageFrom = request.getParameter(JspParameter.FROM_PAGE.toString());
		LOGGER.debug(pageFrom);
		setAttribute(request, pageTo, pageFrom);
		if (pageTo == null || pageTo.isEmpty() || Page.valueOf(pageTo.toUpperCase()) == Page.DEFAULT) {
			return new RedirectToDefaultPageCommand().execute(request, response);
		}
		return Page.valueOf(pageTo.toUpperCase()).getAddress();
	}
	
	private void setAttribute(HttpServletRequest request, String pageTo, String pageFrom) {
		HttpSession session = request.getSession();
		session.setAttribute(Attribute.PAGE_TO.toString(), pageTo);
		if (pageFrom != null && !pageFrom.isEmpty()) {
			session.setAttribute(Attribute.PAGE_FROM.toString(), Page.valueOf(pageFrom));
			LOGGER.debug(Page.valueOf(pageFrom));
		}
	}

}