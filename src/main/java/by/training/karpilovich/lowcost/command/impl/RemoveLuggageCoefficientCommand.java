//package by.training.karpilovich.lowcost.command.impl;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import by.training.karpilovich.lowcost.command.Attribute;
//import by.training.karpilovich.lowcost.command.Command;
//import by.training.karpilovich.lowcost.command.JspParameter;
//import by.training.karpilovich.lowcost.command.Page;
//import by.training.karpilovich.lowcost.entity.Flight;
//import by.training.karpilovich.lowcost.exception.ServiceException;
//
//public class RemoveLuggageCoefficientCommand implements Command {
//
//	@Override
//	public String execute(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		Flight flight = (Flight) session.getAttribute(Attribute.FLIGHT.toString());
//		String boundFrom = request.getParameter(JspParameter.BOUND_FROM.toString());
//		String boundTo = request.getParameter(JspParameter.BOUND_TO.toString());
//		try {
//			getFlightCreatorService().removeLuggageCoefficient(flight, boundFrom, boundTo);
//		} catch (ServiceException e) {
//			setErrorMessage(request, response.getLocale(), e.getMessage());
//		}
//		return Page.FLIGHT_PREVIEW.getAddress();
//	}
//}