package by.training.karpilovich.lowcost.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.builder.TicketBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.TicketDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Ticket;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class TicketDAOImpl implements TicketDAO {

	private static final String ADD_TICKET_CALL_QUERY = "{call add_ticket ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

	private static final String ADD_TICKET_USER_EMAIL_PARAMETER = "user_email";
	private static final String ADD_TICKET_PRICE_PARAMETER = "price";
	private static final String ADD_TICKET_PASSENGER_FIRST_NAME_PARAMETER = "passenger_first_name";
	private static final String ADD_TICKET_PASSENGER_LAST_NAME_PARAMETER = "passenger_last_name";
	private static final String ADD_TICKET_PASSPORT_NUMBER_PARAMETER = "passport_number";
	private static final String ADD_TICKET_LUGGAGE_QUANTITY_PARAMETER = "luggage_quantity";
	private static final String ADD_TICKET_LUGGAGE_PRICE_PARAMETER = "luggage_price";
	private static final String ADD_TICKET_BOARDING_RIGHT_PARAMETER = "primary_boarding_right";
	private static final String ADD_TICKET_FLIGHT_ID_PARAMETER = "flight_id";
	private static final String ADD_TICKET_TICKET_PRICE_PARAMETER = "ticket_price";
	private static final String ADD_TICKET_TICKET_NUMBER_PARAMETER = "ticket_number";
	private static final String ADD_TICKET_PURCHASE_DATE_PARAMETER = "ticket_purchase_date";

	private static final String DELETE_TICKET_CALL_QUERY = "{call remove_ticket (?, ?, ?, ?)  }";

	private static final String DELETE_TICKET_TICKET_NUMBER_PARAMETER = "deleted_ticket_number";
	private static final String DELETE_TICKET_USER_EMAIL_PARAMETER = "user_email";
	private static final String DELETE_TICKET_FLIGHT_ID_PARAMETER = "flight_id";
	private static final String DELETE_TICKET_IS_DELETED_PARAMETER = "is_deleted";

	private static final String SELECT_TICKET_QUERY = " SELECT "
			+ "			  ticket_number, price, purchase_date, passenger_first_name, passenger_last_name, passport_number, "
			+ "			  luggage_quantity, luggage_price, primary_boarding_right, purchase_date, user_email, "
			+ "			  flight.id AS flight_id, flight.number AS flight_number, flight.date AS departure_date, "
			+ "			  city_from.id AS from_id, city_from.name AS from_name, city_from.country_name AS from_country,"
			+ "			  city_to.id AS to_id, city_to.name AS to_name, city_to.country_name AS to_country "
			+ "			  FROM ticket " + "			  JOIN flight ON ticket.flight_id = flight.id "
			+ "			  JOIN city AS city_from ON flight.from_id = city_from.id "
			+ "			  JOIN city AS city_to ON flight.to_id = city_to.id ";

	private static final String SELECT_TICKET_BY_EMAIL_QUERY = SELECT_TICKET_QUERY + "			  WHERE user_email = ?";

	private static final int SELECT_TICKET_BY_EMAIL_QUERY_EMAIL_INDEX = 1;

	private static final String SELECT_TICKET_BY_FLIGHT_ID_QUERY = SELECT_TICKET_QUERY
			+ "			  WHERE flight_id = ?";

	private static final int SELECT_TICKET_BY_FLIGHT_ID_QUERY_ID_INDEX = 1;

	private static final String SELECT_TICKET_BY_NUMBER_QUERY = SELECT_TICKET_QUERY
			+ "			  WHERE ticket_number = ?";

	private static final int SELECT_TICKET_BY_NUMBER_QUERY_NUMBER_INDEX = 1;

	private static final String SELECT_TICKET_TICKET_USER_EMAIL_ROW = "user_email";
	private static final String SELECT_TICKET_TICKET_NUMBER_ROW = "ticket_number";
	private static final String SELECT_TICKET_PRICE_ROW = "price";
	private static final String SELECT_TICKET_PASSENGER_FIRST_NAME_ROW = "passenger_first_name";
	private static final String SELECT_TICKET_PASSENGER_LAST_NAME_ROW = "passenger_last_name";
	private static final String SELECT_TICKET_PASSENGER_PASSPORT_NUMBER_ROW = "passport_number";
	private static final String SELECT_TICKET_PURCHASE_DATE_ROW = "purchase_date";
	private static final String SELECT_TICKET_LUGGAGE_QUANTITY_ROW = "luggage_quantity";
	private static final String SELECT_TICKET_LUGGAGE_PRICE_ROW = "luggage_price";
	private static final String SELECT_TICKET_PRIMARY_BOARDING_ROW = "primary_boarding_right";
	private static final String SELECT_TICKET_FLIGHT_ID_ROW = "flight_id";
	private static final String SELECT_TICKET_FLIGHT_NUMBER_ROW = "flight_number";
	private static final String SELECT_TICKET_DEPARTURE_DATE_ROW = "departure_date";
	private static final String SELECT_TICKET_CITY_FROM_ID_ROW = "from_id";
	private static final String SELECT_TICKET_CITY_FROM_NAME_ROW = "from_name";
	private static final String SELECT_TICKET_CITY_FROM_COUNTRY_ROW = "from_country";
	private static final String SELECT_TICKET_CITY_TO_ID_ROW = "to_id";
	private static final String SELECT_TICKET_CITY_TO_NAME_ROW = "to_name";
	private static final String SELECT_TICKET_CITY_TO_COUNTRY_ROW = "to_country";

	private static final String SELECT_USER_EMAIL_AND_TICKET_NUMBERS_BY_FLIGHT_ID = "SELECT DISTINCT user_email FROM ticket "
			+ " WHERE flight_id = ? ";

	private static final int SELECT_USER_EMAIL_AND_TICKET_NUMBERS_BY_FLIGHT_ID_ID_INDEX = 1;

	private static final Logger LOGGER = LogManager.getLogger(TicketDAOImpl.class);

	private ConnectionPool pool = ConnectionPool.getInstance();

	private TicketDAOImpl() {
	}

	private static final class TicketDAOImplInstanceHolder {
		private static final TicketDAOImpl INSTANCE = new TicketDAOImpl();
	}

	public static TicketDAOImpl getInstance() {
		return TicketDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public List<Ticket> buyTickets(Map<Ticket, BigDecimal> ticketsAndPrices) throws DAOException {
		try (Connection connection = pool.getConnection();
				CallableStatement statement = connection.prepareCall(ADD_TICKET_CALL_QUERY);) {
			return addTicketsToDataSource(ticketsAndPrices, statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a ticket ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public boolean returnTicket(Ticket ticket) throws DAOException {
		try (Connection connection = pool.getConnection();
				CallableStatement statement = connection.prepareCall(DELETE_TICKET_CALL_QUERY);) {
			prepareDeleteStatement(statement, ticket);
			statement.execute();
			return statement.getBoolean(DELETE_TICKET_IS_DELETED_PARAMETER);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while deleting the ticket " + ticket, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public List<Ticket> getTicketByEmail(String email) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_EMAIL_QUERY);) {
			statement.setString(SELECT_TICKET_BY_EMAIL_QUERY_EMAIL_INDEX, email);
			return executeSelectTicketsQuery(statement);
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting ticket by email. email=" + email, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public List<Ticket> getTicketsByFlightId(int id) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_FLIGHT_ID_QUERY);) {
			statement.setInt(SELECT_TICKET_BY_FLIGHT_ID_QUERY_ID_INDEX, id);
			return executeSelectTicketsQuery(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while getting tickets by flight id=" + id, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public Optional<Ticket> getTicketByNumber(long ticketNumber) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_NUMBER_QUERY);) {
			statement.setLong(SELECT_TICKET_BY_NUMBER_QUERY_NUMBER_INDEX, ticketNumber);
			return executeGetTicketStatement(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while getting ticket by number " + ticketNumber, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public List<String> getPossessorsOfTicketToFlightEmails(Flight flight) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(SELECT_USER_EMAIL_AND_TICKET_NUMBERS_BY_FLIGHT_ID);) {
			prepareSelectUserEmailAndTicketNumbersQuery(statement, flight);
			return exequteGetPossessorsOfTicketToFlightEmailsQuery(statement);
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while getting user emails and ticket numbers by fligth id=" + flight.getId(), e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private List<Ticket> addTicketsToDataSource(Map<Ticket, BigDecimal> ticketsAndPrices, CallableStatement statement)
			throws SQLException {
		List<Ticket> tickets = new ArrayList<>(ticketsAndPrices.size());
		for (Entry<Ticket, BigDecimal> ticketAndPricesEntry : ticketsAndPrices.entrySet()) {
			addTicketToDataSource(statement, ticketAndPricesEntry.getKey(), ticketAndPricesEntry.getValue());
			setNumberAndDateToNewAddedTicket(statement, ticketAndPricesEntry.getKey());
			tickets.add(ticketAndPricesEntry.getKey());
		}
		return tickets;
	}

	private void addTicketToDataSource(CallableStatement statement, Ticket ticket, BigDecimal ticketPrice)
			throws SQLException {
		prepareAddStatement(statement, ticket, ticketPrice);
		statement.execute();
	}

	private void setNumberAndDateToNewAddedTicket(CallableStatement statement, Ticket ticket) throws SQLException {
		ticket.setNumber(statement.getLong(ADD_TICKET_TICKET_NUMBER_PARAMETER));
		ticket.setPurchaseDate(takeCalendarFromTimestamp(statement.getTimestamp(ADD_TICKET_PURCHASE_DATE_PARAMETER)));
	}

	private void prepareAddStatement(CallableStatement statement, Ticket ticket, BigDecimal price) throws SQLException {
		statement.setString(ADD_TICKET_USER_EMAIL_PARAMETER, ticket.getEmail());
		statement.setBigDecimal(ADD_TICKET_PRICE_PARAMETER, ticket.getPrice());
		statement.setString(ADD_TICKET_PASSENGER_FIRST_NAME_PARAMETER, ticket.getPassengerFirstName());
		statement.setString(ADD_TICKET_PASSENGER_LAST_NAME_PARAMETER, ticket.getPassengerLastName());
		statement.setString(ADD_TICKET_PASSPORT_NUMBER_PARAMETER, ticket.getPassengerPassportNumber());
		statement.setInt(ADD_TICKET_LUGGAGE_QUANTITY_PARAMETER, ticket.getLuggageQuantity());
		statement.setBigDecimal(ADD_TICKET_LUGGAGE_PRICE_PARAMETER, ticket.getOverweightLuggagePrice());
		statement.setBoolean(ADD_TICKET_BOARDING_RIGHT_PARAMETER, ticket.isPrimaryBoargingRight());
		statement.setInt(ADD_TICKET_FLIGHT_ID_PARAMETER, ticket.getFlight().getId());
		statement.setBigDecimal(ADD_TICKET_TICKET_PRICE_PARAMETER, price);
		statement.registerOutParameter(ADD_TICKET_PURCHASE_DATE_PARAMETER, Types.TIMESTAMP);
		statement.registerOutParameter(ADD_TICKET_TICKET_NUMBER_PARAMETER, Types.BIGINT);
	}

	private void prepareDeleteStatement(CallableStatement statement, Ticket ticket) throws SQLException {
		statement.setLong(DELETE_TICKET_TICKET_NUMBER_PARAMETER, ticket.getNumber());
		statement.setString(DELETE_TICKET_USER_EMAIL_PARAMETER, ticket.getEmail());
		statement.setInt(DELETE_TICKET_FLIGHT_ID_PARAMETER, ticket.getFlight().getId());
		statement.registerOutParameter(DELETE_TICKET_IS_DELETED_PARAMETER, Types.BOOLEAN);
	}

	private void prepareSelectUserEmailAndTicketNumbersQuery(PreparedStatement statement, Flight flight)
			throws SQLException {
		statement.setInt(SELECT_USER_EMAIL_AND_TICKET_NUMBERS_BY_FLIGHT_ID_ID_INDEX, flight.getId());
	}

	private List<Ticket> executeSelectTicketsQuery(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getTicketsFromResultSet(resultSet);
		}
	}

	private List<Ticket> getTicketsFromResultSet(ResultSet resultSet) throws SQLException {
		List<Ticket> tickets = new ArrayList<>();
		while (resultSet.next()) {
			tickets.add(buildTicket(resultSet));
		}
		return tickets;
	}

	private Optional<Ticket> executeGetTicketStatement(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getTicketFromResultSet(resultSet);
		}
	}

	private Optional<Ticket> getTicketFromResultSet(ResultSet resultSet) throws SQLException {
		return resultSet.next() ? Optional.of(buildTicket(resultSet)) : Optional.empty();
	}

	private Ticket buildTicket(ResultSet resultSet) throws SQLException {
		TicketBuilder builder = new TicketBuilder();
		builder.setEmail(resultSet.getString(SELECT_TICKET_TICKET_USER_EMAIL_ROW));
		builder.setNumber(resultSet.getLong(SELECT_TICKET_TICKET_NUMBER_ROW));
		builder.setPrice(resultSet.getBigDecimal(SELECT_TICKET_PRICE_ROW));
		builder.setPurshaseDate(takeCalendarFromTimestamp(resultSet.getTimestamp(SELECT_TICKET_PURCHASE_DATE_ROW)));
		builder.setPassengerFirstName(resultSet.getString(SELECT_TICKET_PASSENGER_FIRST_NAME_ROW));
		builder.setPassengerLastName(resultSet.getString(SELECT_TICKET_PASSENGER_LAST_NAME_ROW));
		builder.setPassengerPassportNumber(resultSet.getString(SELECT_TICKET_PASSENGER_PASSPORT_NUMBER_ROW));
		builder.setLuggageQuantity(resultSet.getInt(SELECT_TICKET_LUGGAGE_QUANTITY_ROW));
		builder.setOverweightLuggagePrice(resultSet.getBigDecimal(SELECT_TICKET_LUGGAGE_PRICE_ROW));
		builder.setPrimaryBoardingRight(resultSet.getBoolean(SELECT_TICKET_PRIMARY_BOARDING_ROW));
		builder.setFlight(buildFlight(resultSet));
		return builder.getTicket();
	}

	private Flight buildFlight(ResultSet resultSet) throws SQLException {
		FlightBuilder builder = new FlightBuilder();
		builder.setId(resultSet.getInt(SELECT_TICKET_FLIGHT_ID_ROW));
		builder.setDate(takeCalendarFromTimestamp(resultSet.getTimestamp(SELECT_TICKET_DEPARTURE_DATE_ROW)));
		builder.setFlightNumber(resultSet.getString(SELECT_TICKET_FLIGHT_NUMBER_ROW));
		builder.setFrom(buildCity(resultSet, SELECT_TICKET_CITY_FROM_ID_ROW, SELECT_TICKET_CITY_FROM_NAME_ROW,
				SELECT_TICKET_CITY_FROM_COUNTRY_ROW));
		builder.setTo(buildCity(resultSet, SELECT_TICKET_CITY_TO_ID_ROW, SELECT_TICKET_CITY_TO_NAME_ROW,
				SELECT_TICKET_CITY_TO_COUNTRY_ROW));
		return builder.getFlight();
	}

	private City buildCity(ResultSet resultSet, String idRow, String cityRow, String countryRow) throws SQLException {
		CityBuilder builder = new CityBuilder();
		builder.setCityId(resultSet.getInt(idRow));
		builder.setCityName(resultSet.getString(cityRow));
		builder.setCityCountry(resultSet.getString(countryRow));
		return builder.getCity();
	}

	private Calendar takeCalendarFromTimestamp(Timestamp timestamp) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar;
	}

	private List<String> exequteGetPossessorsOfTicketToFlightEmailsQuery(PreparedStatement statement)
			throws SQLException {
		try (ResultSet resultSet = statement.executeQuery()) {
			return getAllBuyers(resultSet);
		}
	}

	private List<String> getAllBuyers(ResultSet resultSet) throws SQLException {
		List<String> buyers = new ArrayList<>();
		while (resultSet.next()) {
			buyers.add(resultSet.getString(SELECT_TICKET_TICKET_USER_EMAIL_ROW));
		}
		return buyers;
	}
}