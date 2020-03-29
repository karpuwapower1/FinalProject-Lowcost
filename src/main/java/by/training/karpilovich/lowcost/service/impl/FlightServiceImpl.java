package by.training.karpilovich.lowcost.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.PassengerQuantityValidator;

public class FlightServiceImpl implements FlightService {

	private static final Logger LOGGER = LogManager.getLogger(FlightServiceImpl.class);
	
	private FlightDAO flightDAO = DAOFactory.getInstance().getFlightDAO();


	private FlightServiceImpl() {
	}

	private static final class FlightServiceInstanceHolder {
		private static final FlightServiceImpl INSTANCE = new FlightServiceImpl();
	}

	public static FlightService getInstance() {
		return FlightServiceInstanceHolder.INSTANCE;
	}

	@Override
	public void addFlight(String number, String countryFrom, String countryTo, String date, String defaultPrice,
			String model) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFlight(String number, String date) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Flight> getFlight(String from, String to, String date, String passengerQuantity)
			throws ServiceException {
		City cityFrom = getCityFromString(from);
		City cityTo = getCityFromString(to);
		Calendar departureDate = getDateFromString(date); 
		int quantity = getPassengerQuantityFromString(passengerQuantity);
		Validator dateValidator = new DateValidator(departureDate);
		Validator passengerQuantityValidator = new PassengerQuantityValidator(quantity);
		dateValidator.setNext(passengerQuantityValidator);
		try {
			dateValidator.validate();
			Set<Flight> flights = flightDAO.getFlightsByDateAndPassengerQuantityWithoutPlaceAndDateCoefficient(cityFrom, cityTo, departureDate, quantity);
			for (Flight flight : flights) {
				LOGGER.debug(flight.toString());
			}
			return flightDAO.getFlightsByDateAndPassengerQuantityWithoutPlaceAndDateCoefficient(cityFrom, cityTo, departureDate, quantity);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}

	private Calendar getDateFromString(String date) throws ServiceException {
		DateParser dateParser = new DateParser();
		try {
			return dateParser.getDateFromString(date);
		} catch (ParseException e) {
			throw new ServiceException(MessageType.INVALID_DATE.getType(), e);
		}
	}

	private int getPassengerQuantityFromString(String quantity) throws ServiceException {
		try {
			return Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			throw new ServiceException(MessageType.INVALID_PASSEGER_QUNTITY.getType());
		}
	}

	private City getCityFromString(String city) throws ServiceException {
		ServiceFactory factory = ServiceFactory.getInstance();
		CityService cityService = factory.getCityService();
		return cityService.getCity(city);
	}

}
