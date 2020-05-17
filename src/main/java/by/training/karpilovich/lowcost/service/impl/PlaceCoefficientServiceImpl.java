package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.SortedSet;

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
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.coefficient.CoefficientValueValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.coefficient.PlaceCoefficientBoundsValidator;

public class PlaceCoefficientServiceImpl implements PlaceCoefficientService {

	private static final int INCREASE_PLACE_COEFFICIENT_VALUE = 1;
	private static final int MIN_PLACE_COEFFICIENT_VALUE = 0;
	private static final BigDecimal DEFAULT_COEFFICIENT_VALUE = BigDecimal.ONE;

	private PlaceCoefficientDAO placeCoefficientDAO = DAOFactory.getInstance().getPlaceCoefficientDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private PlaceCoefficientServiceImpl() {
	}

	public static PlaceCoefficientServiceImpl getInstance() {
		return PlaceCoefficientServiceImplInstanceHolder.INSTANCE;
	}

	@Override
	public PlaceCoefficient createPlaceCoefficient(int maxPlacesQuantity, String boundFrom, String boundTo,
			String coefficientValue) throws ServiceException {
		int from = serviceUtil.takeIntFromString(boundFrom);
		int to = serviceUtil.takeIntFromString(boundTo);
		BigDecimal value = serviceUtil.takeBigDecimalFromString(coefficientValue);
		Validator validator = createPlaceCoefficientValidator(maxPlacesQuantity, from, to, value);
		try {
			validator.validate();
			return buildPlaceCoefficient(from, to, value);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void addPlaceCoefficientToSet(SortedSet<PlaceCoefficient> coefficients, PlaceCoefficient coefficient)
			throws ServiceException {
		coefficients = serviceUtil.checkAndGetSetPlaceCoefficient(coefficients);
		serviceUtil.checkPlaceCoefficientOnNull(coefficient);
		Validator validator = new PlaceCoefficientBoundsValidator(coefficient.getFrom(), coefficient.getTo(),
				coefficients);
		try {
			validator.validate();
			coefficients.add(coefficient);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void saveCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		checkAndFillPlaceCoefficients(flight, coefficients);
		try {
			placeCoefficientDAO.addCoefficients(flight.getId(), coefficients);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int getNextBoundFromValuePlaceCoefficient(SortedSet<PlaceCoefficient> coefficients) {
		int bound = MIN_PLACE_COEFFICIENT_VALUE;
		if (coefficients != null && !coefficients.isEmpty()) {
			bound = coefficients.last().getTo() + INCREASE_PLACE_COEFFICIENT_VALUE;
		}
		return bound;
	}

	private static final class PlaceCoefficientServiceImplInstanceHolder {
		private static final PlaceCoefficientServiceImpl INSTANCE = new PlaceCoefficientServiceImpl();
	}

	private void checkAndFillPlaceCoefficients(Flight flight, SortedSet<PlaceCoefficient> coefficients) {
		Optional<Integer> optional = checkIfPlaceCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(buildPlaceCoefficient(optional.get(), flight.getAvailablePlaceQuantity(),
					DEFAULT_COEFFICIENT_VALUE));
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
		return new PlaceCoefficientBuilder().setFrom(from).setTo(to).setValue(value).getCoefficient();
	}

	private Validator createPlaceCoefficientValidator(int maxPlacesQuantity, int from, int to, BigDecimal value) {
		return new PlaceCoefficientBoundFromValidator(from, maxPlacesQuantity)
				.setNext(new PlaceCoefficientBoundToValidator(to, from, maxPlacesQuantity))
				.setNext(new CoefficientValueValidator(value));
	}
}