package by.training.karpilovich.lowcost.command.impl.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.JSPParameter;
import by.training.karpilovich.lowcost.command.Page;
import by.training.karpilovich.lowcost.exception.ServiceException;

public class RestorePasswordCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter(JSPParameter.EMAIL);
		try {
			String password = getUserService().getPasswordByEmail(email);
			getEmailSenderService().sendRemindPasswordMessage(email, password, response.getLocale());
		} catch (ServiceException e) {
			setErrorMessage(request, response.getLocale(), e.getMessage());
		}
		return Page.SIGN_IN.getAddress();
	}
}