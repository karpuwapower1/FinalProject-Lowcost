package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.SortedSet;

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
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.coefficient.CoefficientValueValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundFromValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundToValidator;
import by.training.karpilovich.lowcost.validator.coefficient.DateCoefficientBoundsValidator;

public class DateCoefficientServiceImpl implements DateCoefficientService {

	private static final int INCREASE_ONE_DAY_VALUE = 1;
	private static final BigDecimal DEFAULT_COEFFICIENT_VALUE = BigDecimal.ONE;

	private DateCoefficientDAO dateCoefficientDAO = DAOFactory.getInstance().getDateCoefficientDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private DateCoefficientServiceImpl() {
	}

	public static DateCoefficientServiceImpl getInstance() {
		return DateCoefficientServiceImpllInstanceHolder.INSTANCE;
	}

	@Override
	public DateCoefficient createDateCoefficient(Calendar maxBound, String boundFrom, String boundTo,
			String coefficientValue) throws ServiceException {
		Calendar from = serviceUtil.takeDateFromString(boundFrom);
		Calendar to = serviceUtil.takeDateFromString(boundTo);
		BigDecimal value = serviceUtil.takeBigDecimalFromString(coefficientValue);
		Validator validator = createDateCoefficientValidator(maxBound, from, to, value);
		try {
			validator.validate();
			return buildDateCoefficient(from, to, value);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void addDateCoefficientToSet(SortedSet<DateCoefficient> coefficients, DateCoefficient coefficient)
			throws ServiceException {
		coefficients = serviceUtil.checkAndGetSetDateCoefficient(coefficients);
		serviceUtil.checkDateCoefficientOnNull(coefficient);
		Validator validator = new DateCoefficientBoundsValidator(coefficient.getFrom(), coefficient.getTo(),
				coefficients);
		try {
			validator.validate();
			coefficients.add(coefficient);
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
	public Calendar getNextBoundFromValueDateCoefficient(SortedSet<DateCoefficient> coefficients) {
		Calendar calendar = new GregorianCalendar();
		if (coefficients != null && !coefficients.isEmpty()) {
			calendar.setTimeInMillis(coefficients.last().getTo().getTimeInMillis());
			calendar.add(Calendar.DATE, INCREASE_ONE_DAY_VALUE);
		}
		return calendar;
	}

	private static final class DateCoefficientServiceImpllInstanceHolder {
		private static final DateCoefficientServiceImpl INSTANCE = new DateCoefficientServiceImpl();
	}

	private void checkAndFillDateCoefficients(Flight flight, SortedSet<DateCoefficient> coefficients) {
		Optional<Calendar> optional = checkIfDateCoefficientFilled(flight, coefficients);
		if (optional.isPresent()) {
			coefficients.add(buildDateCoefficient(optional.get(), flight.getDate(), DEFAULT_COEFFICIENT_VALUE));
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
		return new DateCoefficientBuilder().setFrom(from).setTo(to).setValue(value).getCoefficient();
	}

	private Validator createDateCoefficientValidator(Calendar maxValue, Calendar from, Calendar to, BigDecimal value) {
		return new DateCoefficientBoundFromValidator(from, maxValue)
				.setNext(new DateCoefficientBoundToValidator(to, from, maxValue))
				.setNext(new CoefficientValueValidator(value));
	}
}