package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.DateCoefficientBuilder;
import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.builder.PlaceCoefficientBuilder;
import by.training.karpilovich.lowcost.dao.PlaneDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.RepositoryException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.factory.RepositoryFactory;
import by.training.karpilovich.lowcost.factory.SpecificationFactory;
import by.training.karpilovich.lowcost.repository.CityRepository;
import by.training.karpilovich.lowcost.service.FlightCreatorService;
import by.training.karpilovich.lowcost.service.util.ServiceConstant;
import by.training.karpilovich.lowcost.specification.Specification;
import by.training.karpilovich.lowcost.util.DateCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.util.PlaceCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.coefficient.CoefficientValueValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.flight.DateValidator;
import by.training.karpilovich.lowcost.validator.flight.LuggageValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberAndDateAbsenceValidator;
import by.training.karpilovich.lowcost.validator.flight.NumberValidator;
import by.training.karpilovich.lowcost.validator.flight.PriceValidator;

public class FlightCreatorServiceImpl implements FlightCreatorService {

	private static final Logger LOGGER = LogManager.getLogger(FlightCreatorServiceImpl.class);

	private FlightCreatorServiceImpl() {
	}

	private static final class FlightServiceInstanceHolder {
		private static final FlightCreatorServiceImpl INSTANCE = new FlightCreatorServiceImpl();
	}

	public static FlightCreatorServiceImpl getInstance() {
		return FlightServiceInstanceHolder.INSTANCE;
	}

	@Override
	public void addDateCoefficient(Flight flight, SortedSet<DateCoefficient> coefficients, String to,
			String value) throws ServiceException {
		Calendar dateFrom = getNextBoundFromValueDateCoefficient(coefficients);
		Calendar dateTo = takeDateFromString(to);
		BigDecimal coefficientValue = takeBigDecimalFromString(value);
		Validator validator = createDateCoefficientValidator(flight, dateFrom, dateTo, coefficientValue);
		if (coefficients == null) {
			coefficients = new TreeSet<>(new DateCoefficientByBoundComparator());
		}
		try {
			validator.validate();
			coefficients.add((buildDateCoefficient(flight.getId(), dateFrom, dateTo, coefficientValue)));
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void addPlaceCoefficient(Flight flight, SortedSet<PlaceCoefficient> coefficients, String to,
			String value) throws ServiceException {
		int boundFrom = getNextBoundFromValuePlaceCoefficient(coefficients);
		int boundTo = takeIntFromString(to);
		BigDecimal coefficientValue = takeBigDecimalFromString(value);
		Validator validator = createPlaceCoefficientValidator(flight, boundFrom, boundTo, coefficientValue);
		if (coefficients == null) {
			coefficients = new TreeSet<>(new PlaceCoefficientByBoundComparator());
		}
		try {
			validator.validate();
			coefficients.add((buildPlaceCoefficient(flight.getId(), boundFrom, boundTo, coefficientValue)));
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Flight createFlight(String number, String fromId, String toId, String date, String defaultPrice,
			String primaryBoardingPrice, String model, String permittedLuggage, String priceForKgOverweight) throws ServiceException {
		City from = takeCityById(fromId);
		City to = takeCityById(toId);
		BigDecimal price = takeBigDecimalFromString(defaultPrice);
		BigDecimal primaryPrice = takeBigDecimalFromString(primaryBoardingPrice);
		BigDecimal overweightPrice = takeBigDecimalFromString(priceForKgOverweight);
		int luggage = takeIntFromString(permittedLuggage);
		Plane planeModel = takePlaneModel(model);
		Calendar departureDate = takeDateFromString(date);
		Validator validator = createFlightValidator(price, primaryPrice, luggage, departureDate, number, overweightPrice);
		try {
			validator.validate();
			return buildFlight(number, from, to, departureDate, price, primaryPrice, planeModel,
					planeModel.getPlaceQuantity(), luggage, overweightPrice);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients) throws ServiceException {
		int bound = ServiceConstant.MIN_PLACE_COEFFICIENT_VALUE;
		if (coefficients != null && !coefficients.isEmpty()) {
			bound = coefficients.last().getTo() + ServiceConstant.INCREASE_PLACE_COEFFICIENT_VALUE;
		}
		return bound;
	}

	@Override
	public Calendar getNextBoundFromValueDateCoefficient(SortedSet<DateCoefficient> coefficients)
			throws ServiceException {
		Calendar calendar = new GregorianCalendar();
		if (coefficients != null && !coefficients.isEmpty()) {
			calendar.setTimeInMillis(coefficients.last().getTo().getTimeInMillis());
			calendar.add(Calendar.DATE, ServiceConstant.INCREASE_ONE_DAY_VALUE);
		}
		LOGGER.debug("next bound = " + DateParser.format(calendar));
		return calendar;
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

	private Plane takePlaneModel(String model) throws ServiceException {
		try {
			PlaneDAO planeDAO = DAOFactory.getInstance().getPlaneDAO();
			Optional<Plane> optional = planeDAO.getPlaneByModelName(model);
			if (optional.isPresent()) {
				return optional.get();
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		throw new ServiceException(MessageType.NO_SUCH_PLANE_MODEL.getMessage());
	}

	private BigDecimal takeBigDecimalFromString(String value) throws ServiceException {
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to BigDecimal " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	private Flight buildFlight(String number, City from, City to, Calendar date, BigDecimal price,
			BigDecimal primaryBoardingPrice, Plane model, int availablePlace, int permittedLuggage, BigDecimal overweightPrice) {
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

	private DateCoefficient buildDateCoefficient(int flightId, Calendar from, Calendar to, BigDecimal value) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder.setFlightId(flightId);
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private PlaceCoefficient buildPlaceCoefficient(int flightId, int from, int to, BigDecimal value) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder.setFlightId(flightId);
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private Validator createDateCoefficientValidator(Flight flight, Calendar from, Calendar to, BigDecimal value) {
		Validator validator = new DateCoefficientBoundFromValidator(from, flight.getDate());
		Validator boundToValidator = new DateCoefficientBoundToValidator(to, from, flight.getDate());
		Validator coefficientValueValidator = new CoefficientValueValidator(value);
		validator.setNext(boundToValidator);
		boundToValidator.setNext(coefficientValueValidator);
		return validator;
	}

	private Validator createPlaceCoefficientValidator(Flight flight, int from, int to, BigDecimal value) {
		Validator validator = new PlaceCoefficientBoundFromValidator(from, flight.getAvailablePlaceQuantity());
		Validator boundToValidator = new PlaceCoefficientBoundToValidator(to, from, flight.getAvailablePlaceQuantity());
		Validator coefficientValueValidator = new CoefficientValueValidator(value);
		validator.setNext(boundToValidator);
		boundToValidator.setNext(coefficientValueValidator);
		return validator;
	}

	private Validator createFlightValidator(BigDecimal price, BigDecimal primaryPrice, int luggage, Calendar date,
			String number, BigDecimal overweightPrice) {
		Validator validator = new PriceValidator(price);
		Validator primaryPriceValidator = new PriceValidator(primaryPrice);
		Validator luggageValidator = new LuggageValidator(luggage);
		Validator dateValidator = new DateValidator(date);
		Validator numberValidator = new NumberValidator(number);
		Validator numberAndDateValidator = new NumberAndDateAbsenceValidator(number, date);
		Validator overweightPriceValidator = new PriceValidator(overweightPrice);
		validator.setNext(primaryPriceValidator);
		primaryPriceValidator.setNext(luggageValidator);
		luggageValidator.setNext(dateValidator);
		dateValidator.setNext(numberValidator);
		numberValidator.setNext(numberAndDateValidator);
		numberAndDateValidator.setNext(overweightPriceValidator);
		return validator;
	}
}