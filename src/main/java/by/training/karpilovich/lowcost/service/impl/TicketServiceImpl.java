package by.training.karpilovich.lowcost.service.impl;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
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
import by.training.karpilovich.lowcost.service.util.ServiceUtil;
import by.training.karpilovich.lowcost.util.FlightByIdComparator;
import by.training.karpilovich.lowcost.util.message.MessageType;
import by.training.karpilovich.lowcost.validator.Validator;
import by.training.karpilovich.lowcost.validator.flight.PriceValidator;
import by.training.karpilovich.lowcost.validator.ticket.BuyerDataValidator;
import by.training.karpilovich.lowcost.validator.ticket.FlightNumberAndDateValidator;
import by.training.karpilovich.lowcost.validator.ticket.LuggagePriceValidator;

public class TicketServiceImpl implements TicketService {

	private FlightCache cache = new FlightCache();
	private TicketDAO ticketDAO = DAOFactory.getInstance().getTicketDAO();
	private ServiceUtil serviceUtil = new ServiceUtil();

	private TicketServiceImpl() {
	}

	public static TicketServiceImpl getInstance() {
		return TicketServiceInstanceHolder.INSTATNCE;
	}

	@Override
	public void bookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		lockPlaces(cache.getOccupiedPlaces(flight), passengerQuantity, flight.getAvailablePlaceQuantity());
	}

	@Override
	public Ticket createTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			String luggageQuantity, String primaryBoardingRight) throws ServiceException {
		serviceUtil.checkUserOnNull(user);
		serviceUtil.checkFlightOnNull(flight);
		int luggage = (luggageQuantity == null || luggageQuantity.isEmpty()) ? 0
				: serviceUtil.takeIntFromString(luggageQuantity);
		boolean boarding = serviceUtil.takeBooleanFromString(primaryBoardingRight);
		Validator validator = new BuyerDataValidator(user.getEmail(), firstName, lastName, passportNumber, luggage);
		try {
			validator.validate();
			return buildTicket(user, flight, firstName, lastName, passportNumber, luggage, boarding);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Ticket> byeTickets(User user, List<Ticket> tickets) throws ServiceException {
		serviceUtil.checkUserOnNull(user);
		serviceUtil.checkCollectionTicketsOnNull(tickets);
		try {
			validateTickets(tickets);
			checkcIfUserHaveEnoughMoney(user, tickets);
			Map<Ticket, BigDecimal> ticketsAndPrices = createTicketAndPriceMap(tickets);
			return ticketDAO.buyTickets(ticketsAndPrices);
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
	public List<Ticket> getAllUserTickets(User user) throws ServiceException {
		serviceUtil.checkUserOnNull(user);
		try {
			return ticketDAO.getTicketByEmail(user.getEmail());
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public void returnTicket(User user, String ticketNumber) throws ServiceException {
		serviceUtil.checkUserOnNull(user);
		try {
			Ticket ticket = getTicketByNumber(ticketNumber);
			checkTicketDate(ticket);
			ticketDAO.returnTicket(ticket);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Ticket> getAllTicketsToFlight(Flight flight) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		try {
			return ticketDAO.getTicketsByFlightId(flight.getId());
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void unbookTicketToFlight(Flight flight, int passengerQuantity) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		unlockPlaces(flight, passengerQuantity);
	}

	@Override
	public Ticket getTicketByNumber(String ticketNumber) throws ServiceException {
		long number = serviceUtil.takeLongFromString(ticketNumber);
		try {
			return getTicketFromoptional(ticketDAO.getTicketByNumber(number));
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal countTicketPrice(List<Ticket> tickets) throws ServiceException {
		serviceUtil.checkCollectionTicketsOnNull(tickets);
		BigDecimal price = BigDecimal.ZERO;
		for (Ticket ticket : tickets) {
			price = price.add(ticket.getOverweightLuggagePrice());
			price = price.add(ticket.getPrice());
		}
		return price;
	}

	@Override
	public List<String> getTicketToFlightHolders(Flight flight) throws ServiceException {
		serviceUtil.checkFlightOnNull(flight);
		try {
			return ticketDAO.getPossessorsOfTicketToFlightEmails(flight);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private static final class TicketServiceInstanceHolder {
		private static final TicketServiceImpl INSTATNCE = new TicketServiceImpl();
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

	private void validateTickets(List<Ticket> tickets) throws ValidatorException, ServiceException {
		for (Ticket ticket : tickets) {
			serviceUtil.checkTicketOnNull(ticket);
			createTicketValidator(ticket).validate();
		}
	}

	private Validator createTicketValidator(Ticket ticket) {
		return new BuyerDataValidator(ticket.getEmail(), ticket.getPassengerFirstName(), ticket.getPassengerLastName(),
				ticket.getPassengerPassportNumber(), ticket.getLuggageQuantity())
						.setNext(new FlightNumberAndDateValidator(ticket.getFlight()))
						.setNext(new PriceValidator(ticket.getPrice()))
						.setNext(new LuggagePriceValidator(ticket.getOverweightLuggagePrice()));
	}

	private Ticket buildTicket(User user, Flight flight, String firstName, String lastName, String passportNumber,
			int luggageQuantity, boolean primaryBoardingRight) {
		return new TicketBuilder().setFlight(flight).setEmail(user.getEmail()).setPassengerFirstName(firstName)
				.setPassengerLastName(lastName).setPassengerPassportNumber(passportNumber)
				.setLuggageQuantity(luggageQuantity)
				.setPrice(countTicketPrice(flight.getPrice(), primaryBoardingRight, flight.getPrimaryBoardingPrice()))
				.setOverweightLuggagePrice(countPriceForOverweigthLuggage(flight.getPermittedLuggageWeigth(),
						luggageQuantity, flight.getPriceForEveryKgOverweight()))
				.setPrimaryBoardingRight(primaryBoardingRight).getTicket();
	}

	private BigDecimal countPriceForOverweigthLuggage(int permittedLuggage, int passengerLuggage,
			BigDecimal priceForKgOverweight) {
		return permittedLuggage < passengerLuggage
				? priceForKgOverweight.multiply(new BigDecimal(passengerLuggage - permittedLuggage))
				: BigDecimal.ZERO;
	}

	private BigDecimal countTicketPrice(BigDecimal price, boolean primaryBoarding, BigDecimal primaryBoardingPrice) {
		return primaryBoarding ? price.add(primaryBoardingPrice) : price;
	}

	private Map<Ticket, BigDecimal> createTicketAndPriceMap(List<Ticket> tickets) {
		Map<Ticket, BigDecimal> ticketsAndPrices = new HashMap<>();
		for (Ticket ticket : tickets) {
			ticketsAndPrices.put(ticket, ticket.getOverweightLuggagePrice().add(ticket.getPrice()));
		}
		return ticketsAndPrices;
	}

	private void checkcIfUserHaveEnoughMoney(User user, List<Ticket> tickets) throws ServiceException {
		if (user.getBalanceAmount().compareTo(countTicketPrice(tickets)) < 0) {
			throw new ServiceException(MessageType.INSUFFICIENT_FUNDS.getMessage());
		}
	}

	private void checkTicketDate(Ticket ticket) throws ServiceException {
		if (ticket.getFlight().getDate().compareTo(new GregorianCalendar()) < 0) {
			throw new ServiceException(MessageType.TICKET_CAN_NOT_BE_RETURNED.getMessage());
		}
	}

	private Ticket getTicketFromoptional(Optional<Ticket> optional) throws ServiceException {
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new ServiceException(MessageType.NO_SUCH_TICKET.getMessage());
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