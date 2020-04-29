package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.FlightByTicketPriceComparator;
import by.training.karpilovich.lowcost.util.FligthByDepartureDateComparator;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.city.CityPresenceValidator;
import by.training.karpilovich.lowcost.validator.flight.AvailablePlaceValidator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.LuggageValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberAndDateAbsenceValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberValidator;
import by.training.karpilovich.lowcost.validator.flight.PassengerQuantityValidator;
import by.training.karpilovich.lowcost.validator.flight.PriceValidator;
import by.training.karpilovich.lowcost.validator.plane.ModelPresenceValidator;

public class FlightServiceImpl implements FlightService {

	private static final Logger LOGGER = LogManager.getLogger(FlightServiceImpl.class);

	private FlightDAO flightDAO = DAOFactory.getInstance().getFlightDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private FlightServiceImpl() {
	}

	private static final class FlightServiceInstanceHolder {
		private static final FlightServiceImpl INSTANCE = new FlightServiceImpl();
	}

	public static FlightService getInstance() {
		return FlightServiceInstanceHolder.INSTANCE;
	}

	@Override
	public Flight createFlight(String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException {
		if (from == null || to == null) {
			throw new ServiceException(MessageType.NO_SUCH_CITY.getMessage());
		}
		if (plane == null) {
			throw new ServiceException(MessageType.NO_SUCH_PLANE_MODEL.getMessage());
		}
		Flight flight = createFlightFromParameters(number, from, to, date, defaultPrice, primaryBoardingPrice, plane,
				permittedLuggage, priceForKgOverweight);
		Validator validator = createFlightValidator(flight);
		try {
			validator.validate();
			return flight;
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void addFlight(Flight flight) throws ServiceException {
		if (flight == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
		Validator validator = createFlightValidator(flight);
		try {
			validator.validate();
			flightDAO.add(flight);
		} catch (DAOException | ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void updateFlight(String flightId, String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException {
		Flight flight = getFlightById(flightId);
		Flight update = createFlightFromParameters(number, from, to, date, defaultPrice, primaryBoardingPrice, plane,
				permittedLuggage, priceForKgOverweight);
		Validator validator = createUpdateFlightValidator(update, flight);
		try {
			validator.validate();
			update.setId(flight.getId());
			flightDAO.update(update);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void removeFlight(String flightId) throws ServiceException {
		Flight flight = getFlightById(flightId);
		try {
			flightDAO.remove(flight);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Flight> getFlight(City from, City to, String date, String passengerQuantity) throws ServiceException {
		if (from == null || to == null) {
			throw new ServiceException(MessageType.NO_SUCH_CITY.getMessage());
		}
		Calendar departureDate = serviceUtil.takeDateFromString(date);
		int quantity = serviceUtil.takeIntFromString(passengerQuantity);
		Validator dateValidator = new DateValidator(departureDate);
		Validator passengerQuantityValidator = new PassengerQuantityValidator(quantity);
		dateValidator.setNext(passengerQuantityValidator);
		try {
			dateValidator.validate();
			return flightDAO.getFlightsByFromToDateAndPassengerQuantity(from, to, departureDate, quantity);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int getFlightCountWithNumberAndDate(String number, Calendar date) throws ServiceException {
		Validator validator = new NumberValidator(number);
		Validator dateValidator = new DateValidator(date);
		validator.setNext(dateValidator);
		try {
			validator.validate();
			return flightDAO.countFlightWithNumberAndDate(number, date);
		} catch (DAOException | ValidatorException e) {
			LOGGER.debug(e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Flight> getAllFlights() throws ServiceException {
		try {
			return flightDAO.getAllFlights();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Flight getFlightById(String flightId) throws ServiceException {
		int id = serviceUtil.takeIntFromString(flightId);
		try {
			Optional<Flight> optional = flightDAO.getFlightById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
			LOGGER.error("Error while getting flight by id. No flight with id=" + id);
			throw new ServiceException(MessageType.NO_SUCH_FLIGHT.getMessage());
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void sortFlightByTicketPrice(List<Flight> flights) throws ServiceException {
		if (flights == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
		Collections.sort(flights, new FlightByTicketPriceComparator());
	}

	@Override
	public void sortFlightByDepartureDate(List<Flight> flights) throws ServiceException {
		if (flights == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
		Collections.sort(flights, new FligthByDepartureDateComparator());
	}

	@Override
	public List<Flight> getFlightsBetweenDates(String dateFrom, String dateTo) throws ServiceException {
		Calendar from = serviceUtil.takeDateFromString(dateFrom);
		Calendar to = serviceUtil.takeDateFromString(dateTo);
		if (from.compareTo(to) > 0) {
			throw new ServiceException(MessageType.INVALID_DATES.getMessage());
		}
		return getFlightsBetweenDates(from, to);
	}

	@Override
	public List<Flight> getNextTwentyForHoursFlights() throws ServiceException {
		final int additionalHoursQuantity = 24;
		Calendar from = new GregorianCalendar();
		Calendar to = new GregorianCalendar();
		to.add(Calendar.HOUR, additionalHoursQuantity);
		return getFlightsBetweenDates(from, to);
	}

	private Flight createFlightFromParameters(String number, City from, City to, String date, String defaultPrice,
			String primaryBoardingPrice, Plane plane, String permittedLuggage, String priceForKgOverweight)
			throws ServiceException {
		BigDecimal price = serviceUtil.takeBigDecimalFromString(defaultPrice);
		BigDecimal primaryPrice = serviceUtil.takeBigDecimalFromString(primaryBoardingPrice);
		BigDecimal overweightPrice = serviceUtil.takeBigDecimalFromString(priceForKgOverweight);
		int luggage = serviceUtil.takeIntFromString(permittedLuggage);
		Calendar departureDate = serviceUtil.takeDateFromString(date);
		return buildFlight(number, from, to, departureDate, price, primaryPrice, plane, plane.getPlaceQuantity(),
				luggage, overweightPrice);
	}

	private Validator createFlightValidator(Flight flight) {
		Validator validator = new PriceValidator(flight.getPrice());
		Validator primaryPriceValidator = new PriceValidator(flight.getPrimaryBoardingPrice());
		Validator luggageValidator = new LuggageValidator(flight.getPermittedLuggageWeigth());
		Validator dateValidator = new DateValidator(flight.getDate());
		Validator numberValidator = new NumberValidator(flight.getNumber());
		Validator numberAndDateValidator = new NumberAndDateAbsenceValidator(flight.getNumber(), flight.getDate());
		Validator overweightPriceValidator = new PriceValidator(flight.getPriceForEveryKgOverweight());
		Validator cityFromValidator = new CityPresenceValidator(flight.getFrom().getName(),
				flight.getFrom().getCountry());
		Validator cityToValidator = new CityPresenceValidator(flight.getTo().getName(), flight.getTo().getCountry());
		Validator planeModelPresenceValidator = new ModelPresenceValidator(flight.getPlaneModel().getModel());
		Validator availablePlacesValidator = new AvailablePlaceValidator(flight.getAvailablePlaceQuantity());
		validator.setNext(primaryPriceValidator);
		primaryPriceValidator.setNext(luggageValidator);
		luggageValidator.setNext(dateValidator);
		dateValidator.setNext(numberValidator);
		numberValidator.setNext(numberAndDateValidator);
		numberAndDateValidator.setNext(overweightPriceValidator);
		overweightPriceValidator.setNext(cityFromValidator);
		cityFromValidator.setNext(cityToValidator);
		cityToValidator.setNext(planeModelPresenceValidator);
		planeModelPresenceValidator.setNext(availablePlacesValidator);
		return validator;
	}

	private Validator createUpdateFlightValidator(Flight old, Flight update) {
		Validator validator = new PriceValidator(update.getPrice());
		Validator primaryPriceValidator = new PriceValidator(update.getPrimaryBoardingPrice());
		Validator luggageValidator = new LuggageValidator(update.getPermittedLuggageWeigth());
		Validator dateValidator = new DateValidator(update.getDate());
		Validator numberValidator = new NumberValidator(update.getNumber());
		Validator overweightPriceValidator = new PriceValidator(update.getPriceForEveryKgOverweight());
		validator.setNext(primaryPriceValidator);
		primaryPriceValidator.setNext(luggageValidator);
		luggageValidator.setNext(dateValidator);
		dateValidator.setNext(numberValidator);
		numberValidator.setNext(overweightPriceValidator);
		if (!update.getNumber().equals(old.getNumber()) || update.getDate().compareTo(old.getDate()) != 0) {
			overweightPriceValidator.setNext(new NumberAndDateAbsenceValidator(update.getNumber(), update.getDate()));
		}
		return validator;
	}

	private Flight buildFlight(String number, City from, City to, Calendar date, BigDecimal price,
			BigDecimal primaryBoardingPrice, Plane model, int availablePlace, int permittedLuggage,
			BigDecimal overweightPrice) {
		FlightBuilder builder = new FlightBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setFlightNumber(number);
		builder.setPrice(price);
		builder.setPermittedLuggageWeight(permittedLuggage);
		builder.setAvailablePlaceQuantity(availablePlace);
		builder.setPlaneModel(model);
		builder.setDate(date);
		builder.setPrimaryBoardingPrice(primaryBoardingPrice);
		builder.setOverweightLuggagePrice(overweightPrice);
		return builder.getFlight();
	}
	
	private List<Flight> getFlightsBetweenDates(Calendar from, Calendar to) throws ServiceException {
		try {
			return flightDAO.getFlightsBetweenDates(from, to);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}