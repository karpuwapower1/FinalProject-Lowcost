package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

import by.training.karpilovich.lowcost.builder.TicketBuilder;
import by.training.karpilovich.lowcost.dao.TicketDAO;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.entity.User;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.exception.ServiceException;
import by.training.karpilovich.lowcost.exception.ValidatorException;
import by.training.karpilovich.lowcost.factory.DAOFactory;
import by.training.karpilovich.lowcost.service.TicketService;
import by.training.karpilovich.lowcost.util.FlightByIdComparator;
import by.training.karpilovich.lowcost.util.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.PriceValidator;
import by.training.karpilovich.lowcost.validator.ticket.FlightNumberAndDateValidator;
import by.training.karpilovich.lowcost.validator.ticket.LuggagePriceValidator;
import by.training.karpilovich.lowcost.validator.ticket.TicketOnNullValidator;
import by.training.karpilovich.lowcost.validator.ticket.UserDataValidator;

public class TicketServiceImpl implements TicketService {

	private FlightCache cache = new FlightCache();
	private TicketDAO ticketDAO = DAOFactory.getInstance().getTicketDAO();

	private TicketServiceImpl() {
	}

	private static final class TicketServiceInstanceHolder {
		private static final TicketServiceImpl INSTATNCE = new TicketServiceImpl();
	}

	public static TicketServiceImpl getInstance() {
		return TicketServiceInstanceHolder.INSTATNCE;
	}

	@Override
	public void bookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException {
		checkFlightOnNull(flight);
		lockPlaces(cache.getOccupiedPlaces(flight), passengerQuantity, flight.getAvailablePlaceQuantity());
	}

	@Override
	public Ticket createTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			String luggageQuantity, String primaryBoardingRight) throws ServiceException {
		checkUserOnNull(user);
		checkFlightOnNull(flight);
		int luggage = takeIntFromString(luggageQuantity);
		boolean boarding = getBooleanFromString(primaryBoardingRight);
		Validator validator = new UserDataValidator(user.getEmail(), firstName, lastName, passportNumber, luggage);
		try {
			validator.validate();
			return buildTicket(user, flight, firstName, lastName, passportNumber, luggage, boarding);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Ticket> byeTickets(User user, List<Ticket> tickets) throws ServiceException {
		checkUserOnNull(user);
		if (tickets == null || tickets.isEmpty()) {
			throw new ServiceException(MessageType.NO_SUCH_TICKET.getMessage());
		}
		Map<Ticket, BigDecimal> ticketsAndPrices = new HashMap<>();
		BigDecimal ticketPrice;
		BigDecimal sum = BigDecimal.ZERO;
		try {
			for (Ticket ticket : tickets) {
				createTicketValidator(ticket).validate();
				ticketPrice = ticket.getOverweightLuggagePrice().add(ticket.getPrice());
				sum = sum.add(ticketPrice);
				ticketsAndPrices.put(ticket, ticketPrice);
			}
			if (user.getBalanceAmount().compareTo(sum) < 0) {
				throw new ServiceException(MessageType.INSUFFICIENT_FUNDS.getMessage());
			}
			return ticketDAO.add(ticketsAndPrices);
		} catch (DAOException | ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		} finally {
			Optional<Flight> optional = cache.getFlightForTicket(tickets.get(0));
			if (optional.isPresent()) {
				unlockPlaces(optional.get(), tickets.size());
			}
		}
	}
	
	@Override
	public List<Ticket> getAllTickets(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException(MessageType.USER_IS_NULL.getMessage());
		}
		try {
			return ticketDAO.getTicketByEmail(user.getEmail());
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public void unbookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException {
		checkFlightOnNull(flight);
		unlockPlaces(flight, passengerQuantity);
	}

	private void lockPlaces(AtomicInteger placesUnderConsideration, int requiredPlaces, int availablePlaces)
			throws ServiceException {
		if (placesUnderConsideration.addAndGet(requiredPlaces) > availablePlaces) {
			placesUnderConsideration.addAndGet(-requiredPlaces);
			throw new ServiceException(MessageType.INSUFFICIENT_PLACE_QUANTITY.getMessage());
		}
	}

	private void unlockPlaces(Flight flight, int quantity) {
		if (cache.getOccupiedPlaces(flight).addAndGet(-quantity) == 0) {
			cache.remove(flight);
		}
	}

	private void checkUserOnNull(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException(MessageType.USER_IS_NULL.getMessage());
		}
	}

	private void checkFlightOnNull(Flight flight) throws ServiceException {
		if (flight == null) {
			throw new ServiceException(MessageType.NULL_FLIGHT.getMessage());
		}
	}

	private Validator createTicketValidator(Ticket ticket) {
		Validator validator = new TicketOnNullValidator(ticket);
		Validator userDateValidator = new UserDataValidator(ticket.getEmail(), ticket.getPassengerFirstName(),
				ticket.getPassengerLastName(), ticket.getPassengerPassportNumber(), ticket.getLuggageQuantity());
		Validator flightValidator = new FlightNumberAndDateValidator(ticket.getFlight());
		Validator priceValidator = new PriceValidator(ticket.getPrice());
		Validator luggagePriceValidator = new LuggagePriceValidator(ticket.getOverweightLuggagePrice());
		validator.setNext(userDateValidator);
		userDateValidator.setNext(flightValidator);
		flightValidator.setNext(priceValidator);
		priceValidator.setNext(luggagePriceValidator);
		return validator;
	}

	private Ticket buildTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			int luggageQuantity, boolean primaryBoardingRight) {
		TicketBuilder builder = new TicketBuilder();
		builder.setFlight(flight);
		builder.setEmail(user.getEmail());
		builder.setPassengerFirstName(firstName);
		builder.setPassengerLastName(lastName);
		builder.setPassengerPassportNumber(passportNumber);
		builder.setLuggageQuantity(luggageQuantity);
		BigDecimal overweightLuggagePrice = BigDecimal.ZERO;
		if (flight.getPermittedLuggageWeigth() < luggageQuantity) {
			overweightLuggagePrice = flight.getPriceForEveryKgOverweight()
					.multiply(new BigDecimal(luggageQuantity - flight.getPermittedLuggageWeigth()));
		}
		builder.setOverweightLuggagePrice(overweightLuggagePrice);
		BigDecimal price = BigDecimal.valueOf(flight.getPrice().doubleValue());
		if (primaryBoardingRight) {
			price = price.add(flight.getPrimaryBoardingPrice());
		}
		builder.setPrice(price);
		builder.setPrimaryBoardingRight(primaryBoardingRight);
		return builder.getTicket();
	}

	private int takeIntFromString(String value) throws ServiceException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ServiceException(MessageType.INVALID_NUMBER_FORMAT.getMessage());
		}
	}

	private boolean getBooleanFromString(String value) {
		return value != null;
	}

	private class FlightCache {

		private final Map<Flight, AtomicInteger> flightsAndPlacesUnderConsiderationMap = new ConcurrentSkipListMap<>(
				new FlightByIdComparator());

		private AtomicInteger getOccupiedPlaces(Flight flight) {
			if (!flightsAndPlacesUnderConsiderationMap.containsKey(flight)) {
				flightsAndPlacesUnderConsiderationMap.put(flight, new AtomicInteger());
			}
			return flightsAndPlacesUnderConsiderationMap.get(flight);
		}

		private Optional<Flight> getFlightForTicket(Ticket ticket) {
			for (Flight flight : flightsAndPlacesUnderConsiderationMap.keySet()) {
				if (flight.getNumber().equals(ticket.getFlight().getNumber())
						&& flight.getDate().equals(ticket.getFlight().getDate())) {
					return Optional.of(flight);
				}
			}
			return Optional.empty();
		}

		private void remove(Flight flight) {
			flightsAndPlacesUnderConsiderationMap.remove(flight);
		}
	}
}