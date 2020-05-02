package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import by.training.karpilovich.lowcost.builder.PlaceCoefficientBuilder;
import by.training.karpilovich.lowcost.dao.PlaceCoefficientDAO;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.PlaceCoefficientService;
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.PlaceCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundsValidator;
import by.training.karpilovich.lowcost.validator.coefficient.CoefficientValueValidator;

public class PlaceCoefficientServiceImpl implements PlaceCoefficientService {
	
	private static final int INCREASE_PLACE_COEFFICIENT_VALUE = 1;
	private static final int MIN_PLACE_COEFFICIENT_VALUE = 0;
	
	private PlaceCoefficientDAO placeCoefficientDAO = DAOFactory.getInstance().getPlaceCoefficientDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private PlaceCoefficientServiceImpl() {
	}

	private static final class PlaceCoefficientServiceImplInstanceHolder {
		private static final PlaceCoefficientServiceImpl INSTANCE = new PlaceCoefficientServiceImpl();
	}

	public static PlaceCoefficientServiceImpl getInstance() {
		return PlaceCoefficientServiceImplInstanceHolder.INSTANCE;
	}

	@Override
	public void addPlaceCoefficientToSet(SortedSet<PlaceCoefficient> coefficients, int maxPlacesQuantity, String from,
			String to, String value) throws ServiceException {
		if (coefficients == null) {
			coefficients = new TreeSet<>(new PlaceCoefficientByBoundComparator());
		}
		int boundFrom = serviceUtil.takeIntFromString(from);
		int boundTo = serviceUtil.takeIntFromString(to);
		BigDecimal coefficientValue = serviceUtil.takeBigDecimalFromString(value);
		Validator validator = createPlaceCoefficientValidator(maxPlacesQuantity, boundFrom, boundTo, coefficientValue);
		Validator boundsValidator = new PlaceCoefficientBoundsValidator(boundFrom, boundTo, coefficients);
		boundsValidator.setNext(validator);
		try {
			boundsValidator.validate();
			coefficients.add(buildPlaceCoefficient(boundFrom, boundTo, coefficientValue));
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void saveCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients)
			throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		checkAndFillPlaceCoefficients(flight, coefficients);
		try {
			placeCoefficientDAO.addCoefficients(flight.getId(), coefficients);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients) throws ServiceException {
		int bound = MIN_PLACE_COEFFICIENT_VALUE;
		if (coefficients != null && !coefficients.isEmpty()) {
			bound = coefficients.last().getTo() + INCREASE_PLACE_COEFFICIENT_VALUE;
		}
		return bound;
	}

	private void checkAndFillPlaceCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) {
		Optional<Integer> optional = checkIfPlaceCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(buildPlaceCoefficient(optional.get(), flight.getAvailablePlaceQuantity(),
					BigDecimal.ONE));
		}
	}

	private Optional<Integer> checkIfPlaceCoefficientFilled(Flight flight, SortedSet<PlaceCoefficient> coefficients) {
		Optional<Integer> optional = Optional.empty();
		if (coefficients.isEmpty()) {
			optional = Optional.of(MIN_PLACE_COEFFICIENT_VALUE);
		} else if (coefficients.last().getTo() != flight.getAvailablePlaceQuantity()) {
			optional = Optional.of(coefficients.last().getTo() + INCREASE_PLACE_COEFFICIENT_VALUE);
		}
		return optional;
	}

	private PlaceCoefficient buildPlaceCoefficient(int from, int to, BigDecimal value) {
		PlaceCoefficientBuilder builder = new PlaceCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private Validator createPlaceCoefficientValidator(int maxPlacesQuantity, int from, int to, BigDecimal value) {
		Validator validator = new PlaceCoefficientBoundFromValidator(from, maxPlacesQuantity);
		Validator boundToValidator = new PlaceCoefficientBoundToValidator(to, from, maxPlacesQuantity);
		Validator coefficientValueValidator = new CoefficientValueValidator(value);
		validator.setNext(boundToValidator);
		boundToValidator.setNext(coefficientValueValidator);
		return validator;
	}
}