package by.training.karpilovich.lowcost.command;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.karpilovich.lowcost.command.impl.RedirectToDefaultPageCommand;
import by.training.karpilovich.lowcost.command.impl.ShowAllCitiesCommand;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.UserService;
import by.training.karpilovich.lowcost.util.LocaleMessageManager;

public interface Command {

	default void setErrorMessage(HttpServletRequest request, Locale locale, String key) {
		Optional<String> message = LocaleMessageManager.getMessage(key, locale);
		if (message.isPresent()) {
			request.setAttribute(Attribute.ERROR_MESSAGE.toString(), message.get());
		}
	}

	default String getReturnedPageAddress(Page page, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ( page == null) {
			return new RedirectToDefaultPageCommand().execute(request, response);
		}
		String returnedPage;
		switch (page) {
		case DEFAULT:
			returnedPage = new RedirectToDefaultPageCommand().execute(request, response);
			break;
		case ALL_CITIES:
			returnedPage = new ShowAllCitiesCommand().execute(request, response);
			break;
		default:
			returnedPage = page.getAddress();
		}
		return returnedPage;
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

	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
