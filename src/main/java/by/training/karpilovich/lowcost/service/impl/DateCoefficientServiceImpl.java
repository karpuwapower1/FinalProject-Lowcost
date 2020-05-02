package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import by.training.karpilovich.lowcost.builder.DateCoefficientBuilder;
import by.training.karpilovich.lowcost.dao.DateCoefficientDAO;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.DateCoefficientService;
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.DateCoefficientByBoundComparator;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundsValidator;
import by.training.karpilovich.lowcost.validator.coefficient.CoefficientValueValidator;

public class DateCoefficientServiceImpl implements DateCoefficientService {

	private static final int INCREASE_ONE_DAY_VALUE = 1;

	private DateCoefficientDAO dateCoefficientDAO = DAOFactory.getInstance().getDateCoefficientDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private DateCoefficientServiceImpl() {
	}

	private static final class DateCoefficientServiceImpllInstanceHolder {
		private static final DateCoefficientServiceImpl INSTANCE = new DateCoefficientServiceImpl();
	}

	public static DateCoefficientServiceImpl getInstance() {
		return DateCoefficientServiceImpllInstanceHolder.INSTANCE;
	}

	@Override
	public void addDateCoefficientToSet(SortedSet<DateCoefficient> coefficients, Calendar maxValue, String from,
			String to, String value) throws ServiceException {
		if (coefficients == null) {
			coefficients = new TreeSet<>(new DateCoefficientByBoundComparator());
		}
		Calendar boundFrom = serviceUtil.takeDateFromString(from);
		Calendar boundTo = serviceUtil.takeDateFromString(to);
		BigDecimal coefficientValue = serviceUtil.takeBigDecimalFromString(value);
		Validator validator = createDateCoefficientValidator(maxValue, boundFrom, boundTo, coefficientValue);
		Validator dateCoefficientBoundsValidator = new DateCoefficientBoundsValidator(boundFrom, boundTo, coefficients);
		dateCoefficientBoundsValidator.setNext(validator);
		try {
			dateCoefficientBoundsValidator.validate();
			coefficients.add((buildDateCoefficient(boundFrom, boundTo, coefficientValue)));
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void saveCoefficients(Flight flight, SortedSet<DateCoefficient> coefficients) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		checkAndFillDateCoefficients(flight, coefficients);
		try {
			dateCoefficientDAO.addCoefficients(flight.getId(), coefficients);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Calendar getNextBoundFromValueDateCoefficient(SortedSet<DateCoefficient> coefficients)
			throws ServiceException {
		Calendar calendar = new GregorianCalendar();
		if (coefficients != null && !coefficients.isEmpty()) {
			calendar.setTimeInMillis(coefficients.last().getTo().getTimeInMillis());
			calendar.add(Calendar.DATE, INCREASE_ONE_DAY_VALUE);
		}
		return calendar;
	}

	private void checkAndFillDateCoefficients(Flight flight, SortedSet<DateCoefficient> coefficients) {
		Optional<Calendar> optional = checkIfDateCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(buildDateCoefficient(optional.get(), flight.getDate(), BigDecimal.ONE));
		}
	}

	private Optional<Calendar> checkIfDateCoefficientFilled(Flight flight, SortedSet<DateCoefficient> coefficients) {
		Optional<Calendar> optional = Optional.empty();
		if (coefficients.isEmpty()) {
			optional = Optional.of(new GregorianCalendar());
		} else if (!coefficients.last().getTo().equals(flight.getDate())) {
			Calendar date = coefficients.last().getTo();
			date.add(Calendar.DATE, INCREASE_ONE_DAY_VALUE);
			optional = Optional.of(date);
		}
		return optional;
	}

	private DateCoefficient buildDateCoefficient(Calendar from, Calendar to, BigDecimal value) {
		DateCoefficientBuilder builder = new DateCoefficientBuilder();
		builder.setFrom(from);
		builder.setTo(to);
		builder.setValue(value);
		return builder.getCoefficient();
	}

	private Validator createDateCoefficientValidator(Calendar maxValue, Calendar from, Calendar to, BigDecimal value) {
		Validator validator = new DateCoefficientBoundFromValidator(from, maxValue);
		Validator boundToValidator = new DateCoefficientBoundToValidator(to, from, maxValue);
		Validator coefficientValueValidator = new CoefficientValueValidator(value);
		validator.setNext(boundToValidator);
		boundToValidator.setNext(coefficientValueValidator);
		return validator;
	}
}