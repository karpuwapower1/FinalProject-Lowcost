package by.training.karpilovich.lowcost.command;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.service.DateCoefficientService;
import by.training.karpilovich.lowcost.service.EmailSenderService;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.PlaceCoefficientService;
import by.training.karpilovich.lowcost.service.PlaneService;
import by.training.karpilovich.lowcost.service.TicketService;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.util.LocaleMessageManager;

public interface Command {

	default void setErrorMessage(HttpServletRequest request, Locale locale, String key) {
		Optional<String> message = LocaleMessageManager.getMessage(key, locale);
		if (message.isPresent()) {
			HttpSession session = request.getSession();
			session.setAttribute(Attribute.ERROR_MESSAGE.toString(), message.get());
		}
	}

	default UserService getUserService() {
		return ServiceFactory.getInstance().getUserService();
	}

	default FlightService getFlightService() {
		return ServiceFactory.getInstance().getFlightService();
	}

	default CityService getCityService() {
		return ServiceFactory.getInstance().getCityService();
	}

	default PlaneService getPlaneService() {
		return ServiceFactory.getInstance().getPlaneService();
	}

	default TicketService getTicketService() {
		return ServiceFactory.getInstance().getTicketService();
	}

	default PlaceCoefficientService getPlaceCoefficientService() {
		return ServiceFactory.getInstance().getPlaceCoefficientService();
	}

	default DateCoefficientService getDateCoefficientService() {
		return ServiceFactory.getInstance().getDateCoefficientService();
	}
	
	default EmailSenderService getEmailSenderService() {
		return ServiceFactory.getInstance().getEmailSenderService();
	}

	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
