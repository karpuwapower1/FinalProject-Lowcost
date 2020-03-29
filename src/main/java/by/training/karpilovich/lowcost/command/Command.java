package by.training.karpilovich.lowcost.command;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.util.LocaleMessageManager;

public interface Command {
	
	default void setErrorMessage(HttpServletRequest request, Locale locale, String key) {
		Optional<String> message = LocaleMessageManager.getMessage(key, locale);
		if (message.isPresent()) {
			request.setAttribute(AttributeName.ERROR_MESSAGE.getName(), message.get());
		}
	}
	
	default UserService getUserService() {
		ServiceFactory factory = ServiceFactory.getInstance();
		return factory.getUserService();
	}
	
	default FlightService getFlightService() {
		ServiceFactory factory = ServiceFactory.getInstance();
		return factory.getFlightService();
	}
	
	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
