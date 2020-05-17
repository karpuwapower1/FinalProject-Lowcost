package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import by.training.karpilovich.lowcost.util.FlightByDepartureDateComparator;
import by.training.karpilovich.lowcost.util.message.MessageType;
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

	private static final int HOURS_TO_NEXT_DAY_QUANTITY = 24;

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
		serviceUtil.checkCityOnNull(from);
		serviceUtil.checkCityOnNull(to);
		serviceUtil.checkPlaneOnNull(plane);
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
		serviceUtil.checkFlightOnNull(flight);
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
		Validator validator = createUpdateFlightValidator(flight, update);
		try {
			validator.validate();
			update.setId(flight.getId());
			flightDAO.update(update);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void removeFlightAndReturnAllPurchasedTickets(String flightId) throws ServiceException {
		Flight flight = getFlightById(flightId);
		try {
			flightDAO.removeFlightAndReturnAllPurchasedTickets(flight);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Flight> searchFlights(City from, City to, String date, String passengerQuantity)
			throws ServiceException {
		serviceUtil.checkCityOnNull(from);
		serviceUtil.checkCityOnNull(to);
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
			return getFlightFromOptional(flightDAO.getFlightById(id));
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void sortFlightByTicketPrice(List<Flight> flights) throws ServiceException {
		serviceUtil.checkCollectionFlightsOnNull(flights);
		sortFlights(flights, new FlightByTicketPriceComparator());
	}

	@Override
	public void sortFlightByDepartureDate(List<Flight> flights) throws ServiceException {
		serviceUtil.checkCollectionFlightsOnNull(flights);
		sortFlights(flights, new FlightByDepartureDateComparator());
	}

	@Override
	public List<Flight> searchFlightsBetweenDates(String dateFrom, String dateTo) throws ServiceException {
		Calendar from = serviceUtil.takeDateFromString(dateFrom);
		Calendar to = serviceUtil.takeDateFromString(dateTo);
		if (from.compareTo(to) > 0) {
			throw new ServiceException(MessageType.INVALID_DATES.getMessage());
		}
		return getFlightsBetweenDates(from, to);
	}

	@Override
	public List<Flight> searchNextTwentyForHoursFlights() throws ServiceException {
		Calendar from = new GregorianCalendar();
		Calendar to = new GregorianCalendar();
		to.add(Calendar.HOUR, HOURS_TO_NEXT_DAY_QUANTITY);
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
		return new PriceValidator(flight.getPrice()).setNext(new PriceValidator(flight.getPrimaryBoardingPrice()))
				.setNext(new LuggageValidator(flight.getPermittedLuggageWeigth()))
				.setNext(new DateValidator(flight.getDate())).setNext(new NumberValidator(flight.getNumber()))
				.setNext(new NumberAndDateAbsenceValidator(flight.getNumber(), flight.getDate()))
				.setNext(new PriceValidator(flight.getPriceForEveryKgOverweight()))
				.setNext(new CityPresenceValidator(flight.getFrom().getName(), flight.getFrom().getCountry()))
				.setNext(new CityPresenceValidator(flight.getTo().getName(), flight.getTo().getCountry()))
				.setNext(new ModelPresenceValidator(flight.getPlaneModel().getModel()))
				.setNext(new AvailablePlaceValidator(flight.getAvailablePlaceQuantity()));
	}

	private Validator createUpdateFlightValidator(Flight old, Flight update) {
		Validator validator = new PriceValidator(update.getPrice())
				.setNext(new PriceValidator(update.getPrimaryBoardingPrice()))
				.setNext(new LuggageValidator(update.getPermittedLuggageWeigth()))
				.setNext(new DateValidator(update.getDate())).setNext(new NumberValidator(update.getNumber()))
				.setNext(new PriceValidator(update.getPriceForEveryKgOverweight()));
		if (!update.getNumber().equals(old.getNumber()) || update.getDate().compareTo(old.getDate()) != 0) {
			validator.setNext(new NumberAndDateAbsenceValidator(update.getNumber(), update.getDate()));
		}
		return validator;
	}

	private Flight buildFlight(String number, City from, City to, Calendar date, BigDecimal price,
			BigDecimal primaryBoardingPrice, Plane model, int availablePlace, int permittedLuggage,
			BigDecimal overweightPrice) {
		return new FlightBuilder().setFrom(from).setTo(to).setFlightNumber(number).setPrice(price)
				.setPermittedLuggageWeight(permittedLuggage).setAvailablePlaceQuantity(availablePlace)
				.setPlaneModel(model).setDate(date).setPrimaryBoardingPrice(primaryBoardingPrice)
				.setOverweightLuggagePrice(overweightPrice).getFlight();
	}

	private Flight getFlightFromOptional(Optional<Flight> optional) throws ServiceException {
		if (optional.isPresent()) {
			return optional.get();
		}
		LOGGER.error("Error while getting flight by id. No such a flight");
		throw new ServiceException(MessageType.NO_SUCH_FLIGHT.getMessage());
	}

	private List<Flight> getFlightsBetweenDates(Calendar from, Calendar to) throws ServiceException {
		try {
			return flightDAO.getFlightsBetweenDates(from, to);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private void sortFlights(List<Flight> flights, Comparator<Flight> comparator) {
		Collections.sort(flights, comparator);
	}
}