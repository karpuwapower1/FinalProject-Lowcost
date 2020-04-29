package by.training.karpilovich.lowcost.command.impl.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;

public class RedirectToPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageTo = request.getParameter(JSPParameter.TO_PAGE);
		String pageFrom = request.getParameter(JSPParameter.FROM_PAGE);
		setAttribute(request, pageTo, pageFrom);
		if (pageTo == null || pageTo.isEmpty() || Page.valueOf(pageTo.toUpperCase()) == Page.DEFAULT) {
			return Page.DEFAULT.getAddress();
		}
		return Page.valueOf(pageTo.toUpperCase()).getAddress();
	}

	private void setAttribute(HttpServletRequest request, String pageTo, String pageFrom) {
		HttpSession session = request.getSession();
		session.setAttribute(Attribute.PAGE_TO.toString(), pageTo);
		if (pageFrom != null && !pageFrom.isEmpty()) {
			session.setAttribute(Attribute.PAGE_FROM.toString(), Page.valueOf(pageFrom));
		}
	}
}