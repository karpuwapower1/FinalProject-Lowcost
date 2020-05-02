package by.training.karpilovich.lowcost.service.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
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

	public void checkFlightOnNull(Flight flight) throws ServiceException {
		if (flight == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
	}

	public void checkCityOnNull(City city) throws ServiceException {
		if (city == null) {
			throw new ServiceException(MessageType.NO_SUCH_CITY.getMessage());
		}
	}

	public void checkPlaneOnNull(Plane plane) throws ServiceException {
		if (plane == null) {
			throw new ServiceException(MessageType.NO_SUCH_PLANE_MODEL.getMessage());
		}
	}

	public void checkCollectionFlightsOnNull(Collection<Flight> flights) throws ServiceException {
		if (flights == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
	}

	public void checkUserOnNull(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException(MessageType.USER_IS_NULL.getMessage());
		}
	}

	public void checkCollectionTicketsOnNull(Collection<Ticket> tickets) throws ServiceException {
		if (tickets == null || tickets.isEmpty()) {
			throw new ServiceException(MessageType.NO_SUCH_TICKET.getMessage());
		}
	}

	public boolean checkEmailAddress(String address) {
		return (address != null && !address.isEmpty());
	}

	public boolean checkEmailAddresses(Collection<String> addresses) {
		if (addresses == null) {
			return false;
		}
		for (String address : addresses) {
			if (!checkEmailAddress(address)) {
				return false;
			}
		}
		return true;
	}

	public void checkTicketOnNull(Ticket ticket) throws ServiceException {
		if (ticket == null) {
			throw new ServiceException(MessageType.NO_SUCH_TICKET.getMessage());
		}
	}
}