package by.training.karpilovich.lowcost.listener;

import java.util.Set;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.command.Attribute;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;

@WebListener
public class SessionListenerImpl implements HttpSessionListener {
	
	private static final Logger LOGGER = LogManager.getLogger(SessionListenerImpl.class);

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		try {
		initializeSession(sessionEvent.getSession());
		} catch (ServiceException e) {
			LOGGER.error(e);
		}
		HttpSessionListener.super.sessionCreated(sessionEvent);
	}
	
	private void initializeSession(HttpSession session) throws ServiceException {
		session.setAttribute(Attribute.CITIES.toString(), getCities());
	}
	
	private Set<City> getCities() throws ServiceException {
		CityService cityService = ServiceFactory.getInstance().getCityService();
		return cityService.getAllCities();
	}
}