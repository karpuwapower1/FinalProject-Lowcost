package by.training.karpilovich.lowcost.command.impl.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class ChangePasswordCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page page = null;
		try {
			changeUserPassword(request);
			page = (Page) request.getSession().getAttribute(Attribute.PAGE_FROM.toString());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			page = Page.CHANGE_PASSWORD;
		}
		return page.getAddress();
	}

	void changeUserPassword(HttpServletRequest request) throws ServiceException {
		String password = request.getParameter(JSPParameter.PASSWORD);
		String repeatPassword = request.getParameter(JSPParameter.REPEAT_PASSWORD);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER.toString());
		user = getUserService().changePassword(user, password, repeatPassword);
		session.setAttribute(Attribute.USER.toString(), user);
	}
}