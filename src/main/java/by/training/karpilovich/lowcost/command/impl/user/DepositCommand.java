package by.training.karpilovich.lowcost.command.impl.user;

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

public class DepositCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER.toString());
		String amount = request.getParameter(JSPParameter.AMOUNT);
		try {
			getUserService().deposit(user, amount);
			return Page.DEFAULT.getAddress();
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
			return Page.DEPOSIT.getAddress();
		}
	}
}