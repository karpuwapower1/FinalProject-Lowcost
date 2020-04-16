package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.DateCoefficientBuilder;
import by.training.karpilovich.lowcost.builder.PlaceCoefficientBuilder;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.factory.RepositoryFactory;
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.service.FlightService;
import by.training.karpilovich.lowcost.service.util.ServiceConstant;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.LuggageValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberAndDateValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberValidator;
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
	public void addFlight(Flight flight, SortedSet<PlaceCoefficient> placeCoefficents,
			SortedSet<DateCoefficient> dateCoefficients) throws ServiceException {
		if (flight == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
		Validator validator = createFlightValidator(flight.getPrice(), flight.getPrimaryBoardingPrice(),
				flight.getPermittedLuggageWeigth(), flight.getDate(), flight.getNumber());
		try {
			validator.validate();
			checkAndFillDateCoefficients(flight, dateCoefficients);
			checkAndFillPlaceCoefficients(flight, placeCoefficents);
			getFlightDAO().add(flight, dateCoefficients, placeCoefficents);
		} catch (DAOException | ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void removeFlight(String number, String date) throws ServiceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Set<Flight> getFlight(String fromId, String toId, String date, String passengerQuantity)
			throws ServiceException {
		City cityFrom = takeCityById(fromId);
		City cityTo = takeCityById(toId);
		Calendar departureDate = takeDateFromString(date);
		int quantity = takeIntFromString(passengerQuantity);
		Validator dateValidator = new DateValidator(departureDate);
		Validator passengerQuantityValidator = new PassengerQuantityValidator(quantity);
		dateValidator.setNext(passengerQuantityValidator);
		try {
			dateValidator.validate();
			return getFlightDAO().getFlightsByFromToDateAndPassengerQuantity(cityFrom, cityTo, departureDate, quantity);
		} catch (ValidatorException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int getFlightCountWithNumberAndDate(String number, Calendar date) throws ServiceException {
		Validator numberValidator = new NumberValidator(number);
		Validator dateValidator = new DateValidator(date);
		numberValidator.setNext(dateValidator);
		FlightDAO flightDao = getFlightDAO();
		try {
			numberValidator.validate();
			return flightDao.countFlightWithNumberAndDate(number, date);
		} catch (DAOException | ValidatorException e) {
			LOGGER.debug(e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Flight> getAllFlights() throws ServiceException {
		try {
			return getFlightDAO().getAllFlights();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private FlightDAO getFlightDAO() {
		return DAOFactory.getInstance().getFlightDAO();
	}

	private City takeCityById(String id) throws ServiceException {
		RepositoryFactory factory = RepositoryFactory.getInstance();
		CityRepository repository = factory.getCityRepository();
		Specification specification = SpecificationFactory.getInstance()
				.getQuerySpecificationById(takeIntFromString(id));
		try {
			City city = repository.getCities(specification).first();
			if (city != null) {
				return city;
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		throw new ServiceException(MessageType.INVALID_CITY.getMessage());
	}

	private Calendar takeDateFromString(String date) throws ServiceException {
		try {
			return DateParser.parse(date);
		} catch (ParseException e) {
			LOGGER.error("Exception while parcing a date " + date, e);
			throw new ServiceException(MessageType.INVALID_DATE.getMessage(), e);
		}
	}

	private int takeIntFromString(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to int " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	private void checkAndFillDateCoefficients(Flight flight, SortedSet<DateCoefficient> coefficients) {
		Optional<Calendar> optional = checkIfDateCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(createDateCoefficient(flight.getId(), optional.get(), flight.getDate(), BigDecimal.ONE));
		}
	}

	private Optional<Calendar> checkIfDateCoefficientFilled(Flight flight, SortedSet<DateCoefficient> coefficients) {
		Optional<Calendar> optional = Optional.empty();
		if (coefficients.isEmpty()) {
			optional = Optional.of(new GregorianCalendar());
		}
		if (!coefficients.last().getTo().equals(flight.getDate())) {
			Calendar date = coefficients.last().getTo();
			date.add(Calendar.DATE, ServiceConstant.INCREASE_ONE_DAY_VALUE);
			optional = Optional.of(date);
		}
		return optional;
	}

	private void checkAndFillPlaceCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) {
		Optional<Integer> optional = checkIfPlaceCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(createPlaceCoefficient(flight.getId(), optional.get(), flight.getAvailablePlaceQuantity(),
					BigDecimal.ONE));
		}
	}

	private Optional<Integer> checkIfPlaceCoefficientFilled(Flight flight, SortedSet<PlaceCoefficient> coefficients) {
		Optional<Integer> optional = Optional.empty();
		if (coefficients.isEmpty()) {
			optional = Optional.of(ServiceConstant.MIN_PLACE_COEFFICIENT_VALUE);
		}
		if (coefficients.last().getTo() != flight.getAvailablePlaceQuantity()) {
			optional = Optional.of(coefficients.last().getTo() + ServiceConstant.INCREASE_PLACE_COEFFICIENT_VALUE);
		}
		return optional;
	}

	private DateCoefficient createDateCoefficient(int id, Calendar from, Calendar to, BigDecimal value) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder.setFlightId(id);
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private PlaceCoefficient createPlaceCoefficient(int id, int from, int to, BigDecimal value) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder.setFlightId(id);
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private Validator createFlightValidator(BigDecimal price, BigDecimal primaryPrice, int luggage, Calendar date,
			String number) {
		Validator validator = new PriceValidator(price);
		Validator primaryPriceValidator = new PriceValidator(primaryPrice);
		Validator luggageValidator = new LuggageValidator(luggage);
		Validator dateValidator = new DateValidator(date);
		Validator numberValidator = new NumberValidator(number);
		Validator numberAndDateValidator = new NumberAndDateValidator(number, date);
		validator.setNext(primaryPriceValidator);
		primaryPriceValidator.setNext(luggageValidator);
		luggageValidator.setNext(dateValidator);
		dateValidator.setNext(numberValidator);
		numberValidator.setNext(numberAndDateValidator);
		return validator;
	}
}