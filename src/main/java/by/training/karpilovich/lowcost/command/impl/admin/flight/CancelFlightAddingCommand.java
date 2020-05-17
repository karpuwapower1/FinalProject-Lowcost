package by.training.karpilovich.lowcost.command.impl.admin.flight;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.command.Command;
import by.training.karpilovich.lowcost.command.impl.redirect.RedirectToCreateFlightPageCommand;

public class CancelFlightAddingCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		removeAttributes(request);
		return new RedirectToCreateFlightPageCommand().execute(request, response);
	}
	
	private void removeAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Attribute.FLIGHT.toString());
		session.removeAttribute(Attribute.DATE_COEFFICIENT.toString());
		session.removeAttribute(Attribute.PLACE_COEFFICIENT.toString());
	}
}