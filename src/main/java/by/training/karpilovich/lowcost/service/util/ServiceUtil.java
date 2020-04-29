package by.training.karpilovich.lowcost.service.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.util.DateParser;
import by.training.karpilovich.lowcost.util.MessageType;

public class ServiceUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(ServiceUtil.class);
	
	public BigDecimal takeBigDecimalFromString(String value) throws ServiceException {
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to BigDecimal " + value);
			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
		}
	}

	public Calendar takeDateFromString(String date) throws ServiceException {
		try {
			return DateParser.parse(date);
		} catch (ParseException e) {
			LOGGER.error("Exception while parcing a date " + date, e);
			throw new ServiceException(MessageType.INVALID_DATE.getMessage(), e);
		}
	}

	public int takeIntFromString(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to int " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}
	
	public long takeLongFromString(String value) throws ServiceException {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			LOGGER.error("Error while formatting string to int " + value);
			throw new ServiceException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}
	
	public boolean takeBooleanFromString(String value) {
		return value != null;
	}
}