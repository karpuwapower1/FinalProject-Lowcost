package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.entity.coefficient.DateCoefficient;
import by.training.karpilovich.lowcost.entity.coefficient.LuggageCoefficient;
import by.training.karpilovich.lowcost.entity.coefficient.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.factory.ServiceFactory;
import by.training.karpilovich.lowcost.service.CityService;
import by.training.karpilovich.lowcost.service.CoefficientService;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.PlaneService;
import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.LuggageValidator;
import by.training.karpilovich.lowcost.validator.flight.PassengerQuantityValidator;
import by.training.karpilovich.lowcost.validator.flight.PriceValidator;

public class FlightServiceImpl implements FlightService {

	private static final Logger LOGGER = LogManager.getLogger(FlightServiceImpl.class);

	private FlightServiceImpl() {
	}

	private static final class FlightServiceInstanceHolder {
		private static final FlightServiceImpl INSTANCE = new FlightServiceImpl();
	}

	public static FlightService getInstance() {
		return FlightServiceInstanceHolder.INSTANCE;
	}

	@Override
	public void addFlight(String number, String fromId, String toId, String date, String defaultPrice, String model,
			String permittedLuggage) throws ServiceException {
		City from = takeCityFromString(fromId);
		City to = takeCityFromString(toId);
		BigDecimal price = takeBigDecimalFromString(defaultPrice);
		int luggage = takeIntFromString(permittedLuggage);
		Plane planeModel = getPlaneModel(model);
		Calendar departureDate = takeDateFromString(date);
		Flight flight = buildFlight(number, from, to, departureDate, price, planeModel, planeModel.getPlaceQuantity(),
				luggage);
		try {
			getFlightDAO().add(flight);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addLuggageCoefficient(Flight flight, String from, String to, String value) throws ServiceException {
		CoefficientService service = getCoefficientService();
		LuggageCoefficient coefficient = service.makeLuggageCoefficientFromParameters(flight.getId(), from, to, value);
		flight.addLuggageCoefficient(coefficient);
	}

	@Override
	public void addDateCoefficient(Flight flight, String from, String to, String value) throws ServiceException {
		CoefficientService service = getCoefficientService();
		DateCoefficient coefficient = service.makeDateCoefficientFromParameters(flight.getId(), from, to, value);
		flight.addDateCoefficient(coefficient);
	}

	@Override
	public void addPlaceCoefficient(Flight flight, String from, String to, String value) throws ServiceException {
		CoefficientService service = getCoefficientService();
		PlaceCoefficient coefficient = service.makePlaceCoefficientFromParameters(flight.getId(), from, to, value);
		flight.addPlaceCoefficient(coefficient);
	}

	@Override
	public void removeFlight(String number, String date) throws ServiceException {
		// TODO Auto-generated method stub

	}

	public Flight makeFlightFromParamenters(String number, String fromId, String toId, String date, String defaultPrice,
			String model, String permittedLuggage) throws ServiceException {
		City from = takeCityFromString(fromId);
		City to = takeCityFromString(toId);
		BigDecimal price = takeBigDecimalFromString(defaultPrice);
		int luggage = takeIntFromString(permittedLuggage);
		Plane planeModel = getPlaneModel(model);
		Calendar departureDate = takeDateFromString(date);
		Validator validator = new PriceValidator(price);
		Validator luggageValidator = new LuggageValidator(luggage);
		Validator dateValidator = new DateValidator(departureDate);
		validator.setNext(luggageValidator);
		luggageValidator.setNext(dateValidator);
		try {
			validator.validate();
			return buildFlight(number, from, to, departureDate, price, planeModel, planeModel.getPlaceQuantity(),
					luggage);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Set<Flight> getFlight(String fromId, String toId, String date, String passengerQuantity)
			throws ServiceException {
		City cityFrom = takeCityFromString(fromId);
		City cityTo = takeCityFromString(toId);
		Calendar departureDate = takeDateFromString(date);
		int quantity = takeIntFromString(passengerQuantity);
		Validator dateValidator = new DateValidator(departureDate);
		Validator passengerQuantityValidator = new PassengerQuantityValidator(quantity);
		dateValidator.setNext(passengerQuantityValidator);
		try {
			dateValidator.validate();
			return getFlightDAO().getFlightsByDateAndPassengerQuantityWithoutPlaceAndDateCoefficient(cityFrom, cityTo,
					departureDate, quantity);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private FlightDAO getFlightDAO() {
		return DAOFactory.getInstance().getFlightDAO();
	}

	private Calendar takeDateFromString(String date) throws ServiceException {
		DateParser dateParser = new DateParser();
		try {
			return dateParser.getDateFromString(date);
		} catch (ParseException e) {
			LOGGER.error("Exception while parcing a date " + date, e);
			throw new ServiceException(MessageType.INVALID_DATE.getMessage(), e);
		}
	}

	private City takeCityFromString(String id) throws ServiceException {
		ServiceFactory factory = ServiceFactory.getInstance();
		CityService cityService = factory.getCityService();
		return cityService.getCityById(takeIntFromString(id));
	}

	private int takeIntFromString(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to int " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	private Plane getPlaneModel(String model) throws ServiceException {
		PlaneService planeService = ServiceFactory.getInstance().getPlaneService();
		return planeService.getPlaneByModel(model);
	}

	private BigDecimal takeBigDecimalFromString(String value) throws ServiceException {
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to BigDecimal " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	private Flight buildFlight(String number, City from, City to, Calendar date, BigDecimal price, Plane model,
			int availablePlace, int permittedLuggage) {
		FlightBuilder builder = new FlightBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setFlightNumber(number);
		builder.setPrice(price);
		builder.setPermittedLuggageWeight(permittedLuggage);
		builder.setAvailablePlaceQuantity(availablePlace);
		builder.setPlaneModel(model);
		builder.setDate(date);
		return builder.getFlight();
	}

	private CoefficientService getCoefficientService() {
		return ServiceFactory.getInstance().getCoefficientService();
	}

}
