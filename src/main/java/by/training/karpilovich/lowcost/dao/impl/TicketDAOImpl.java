package by.training.karpilovich.lowcost.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

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
import by.training.karpilovich.lowcost.util.MessageType;

public class TicketDAOImpl implements TicketDAO {

	private static final String ADD_TICKET_QUERY = "INSERT INTO "
			+ " ticket(`user_email`, `purchase_date`, `price`, `passenger_first_name`, "
			+ " `passenger_last_name`, `passport_number`, `luggage_quantity`, `luggage_price`, `primary_boarding_right`, "
			+ " `flight_id`) " + "  VALUES (?, CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?); ";

	private static final int ADD_TICKET_QUERY_EMAIL_INDEX = 1;
	private static final int ADD_TICKET_QUERY_PRICE_INDEX = 2;
	private static final int ADD_TICKET_QUERY_FIRST_NAME_INDEX = 3;
	private static final int ADD_TICKET_QUERY_LAST_NAME_INDEX = 4;
	private static final int ADD_TICKET_QUERY_PASSPORT_NUMBER_INDEX = 5;
	private static final int ADD_TICKET_QUERY_LUGGAGE_QUANTITY_INDEX = 6;
	private static final int ADD_TICKET_QUERY_LUGGAGE_PRICE_INDEX = 7;
	private static final int ADD_TICKET_QUERY_PRIMARY_BOARDING_INDEX = 8;
	private static final int ADD_TICKET_QUERY_FLIGHT_ID_INDEX = 9;

	private static final String UPDATE_FLIGHT_PASSENGER_QUANTITY = "UPDATE flight "
			+ " SET available_places=available_places - 1 " + " WHERE id=?";

	private static final int UPDATE_FLIGHT_ID_INDEX = 1;

	private static final String UPDATE_USER_BALANCE = "UPDATE airport_user "
			+ " SET balance_amount = balance_amount - ? " + " WHERE email=? ";

	private static final int UPDATE_USER_BALANCE_BALANCE_INDEX = 1;
	private static final int UPDATE_USER_BALANCE_EMAIL_INDEX = 2;

	private static final String UPDATE_AIRPORT_BALANCE = "UPDATE airport_balance " + " SET balance = balance + ? ";

	private static final int UPDATE_AIRPORT_BALANCE_BALANCE_INDEX = 1;

	private static final String INSERT_TRANSACTION = "INSERT INTO "
			+ " transactions(from_id, date, ticket_number, amount) " + " VALUES(?,?,?,?)";

	private static final int INSERT_TRANSACTION_FROM_INDEX = 1;
	private static final int INSERT_TRANSACTION_DATE_INDEX = 2;
	private static final int INSERT_TRANSACTION_TISKET_INDEX = 3;
	private static final int INSERT_TRANSACTION_AMOUNT_INDEX = 4;

	private static final String SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_ID = " SELECT "
			+ " LAST_INSERT_ID() AS ticket_number, purchase_date " + " FROM ticket "
			+ " WHERE ticket_number=LAST_INSERT_ID()";

	private static final String RESUL_SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_ID_TICKET_NUMBER = "ticket_number";
	private static final String RESUL_SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_DATE = "purchase_date";

	private static final String SELECT_TICKET_BY_EMAIL_QUERY = " SELECT "
			+ "			  ticket_number, price, purchase_date, passenger_first_name, passenger_last_name, passport_number, "
			+ "			  luggage_quantity, luggage_price, primary_boarding_right, purchase_date, "
			+ "			  flight.id AS flight_id, flight.number AS flight_number, flight.date AS departure_date, "
			+ "			  city_from.id AS from_id, city_from.name AS from_name, city_from.country_name AS from_country,"
			+ "			  city_to.id AS to_id, city_to.name AS to_name, city_to.country_name AS to_country "
			+ "			  FROM ticket " + "			  JOIN flight ON ticket.flight_id = flight.id "
			+ "			  JOIN city AS city_from ON flight.from_id = city_from.id "
			+ "			  JOIN city AS city_to ON flight.to_id = city_to.id " + "			  WHERE user_email = ?";

	private static final int SELECT_TICKET_BY_EMAIL_QUERY_EMAIL_INDEX = 1;

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

	private static final Logger LOGGER = LogManager.getLogger(TicketDAOImpl.class);

	private TicketDAOImpl() {
	}

	private static final class TicketDAOImplInstanceHolder {
		private static final TicketDAOImpl INSTANCE = new TicketDAOImpl();
	}

	public static TicketDAOImpl getInstance() {
		return TicketDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public List<Ticket> add(Map<Ticket, BigDecimal> ticketsAndPricesMap) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = null;
		List<Ticket> tickets = new ArrayList<>();
		try {
			connection = pool.getConnection();
			connection.setAutoCommit(false);
			for (Ticket ticket : ticketsAndPricesMap.keySet()) {
				addTicket(ticket, connection);
				transfer(ticket, connection);
				tickets.add(ticket);
				updateFlightPlacesQuantity(ticket.getFlight().getId(), connection);
			}
			return tickets;
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a tickets " + tickets, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Error while closing a connection", e);
				}
			}
		}
	}

	@Override
	public void deleteTicket(Ticket ticket) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Ticket> getTicketByEmail(String email) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_EMAIL_QUERY);) {
			statement.setString(SELECT_TICKET_BY_EMAIL_QUERY_EMAIL_INDEX, email);
			try (ResultSet resultSet = statement.executeQuery()) {
				List<Ticket> tickets = new ArrayList<>();
				while (resultSet.next()) {
					tickets.add(buildTicket(resultSet, email));
				}
				return tickets;
			}
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting ticket by email. email=" + email, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		}
	}

	@Override
	public List<Ticket> getTicketToFlight(Flight flight) throws DAOException {
		throw new UnsupportedOperationException();
	}

	private void addTicket(Ticket ticket, Connection connection) throws SQLException {
		try (PreparedStatement addTicketStatement = connection.prepareStatement(ADD_TICKET_QUERY);
				PreparedStatement selectDateAndTicketNumber = connection
						.prepareStatement(SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_ID);) {
			prepareAddTicketStatement(addTicketStatement, ticket);
			addTicketStatement.executeUpdate();
			try (ResultSet resultSet = selectDateAndTicketNumber.executeQuery()) {
				resultSet.next();
				ticket.setPurchaseDate(takeCalendarFromTimestamp(
						resultSet.getTimestamp(RESUL_SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_DATE)));
				ticket.setNumber(
						resultSet.getLong(RESUL_SELECT_TICKET_NUMBER_AND_PURCHASE_DATE_BY_FLIGHT_ID_TICKET_NUMBER));
			}
		}
	}

	private void transfer(Ticket ticket, Connection connection) throws SQLException {
		try (PreparedStatement updateUserBalance = connection.prepareStatement(UPDATE_USER_BALANCE);
				PreparedStatement updateAirlinesBalance = connection.prepareStatement(UPDATE_AIRPORT_BALANCE);
				PreparedStatement addTransaction = connection.prepareStatement(INSERT_TRANSACTION)) {
			BigDecimal price = ticket.getPrice().add(ticket.getOverweightLuggagePrice());
			prepareUpdateUserBalanceStatement(updateUserBalance, ticket.getEmail(), price);
			prepareUpdateAirlinesBalanceStatement(updateAirlinesBalance, price);
			prepareAddTransactionStatement(addTransaction, ticket, price);
			updateUserBalance.executeUpdate();
			updateAirlinesBalance.executeUpdate();
			addTransaction.executeUpdate();
		}
	}

	private void updateFlightPlacesQuantity(int id, Connection connection) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_FLIGHT_PASSENGER_QUANTITY)) {
			statement.setInt(UPDATE_FLIGHT_ID_INDEX, id);
			statement.executeUpdate();
		}
	}

	private void prepareAddTicketStatement(PreparedStatement statement, Ticket ticket) throws SQLException {
		statement.setString(ADD_TICKET_QUERY_EMAIL_INDEX, ticket.getEmail());
		statement.setBigDecimal(ADD_TICKET_QUERY_PRICE_INDEX, ticket.getPrice());
		statement.setString(ADD_TICKET_QUERY_FIRST_NAME_INDEX, ticket.getPassengerFirstName());
		statement.setString(ADD_TICKET_QUERY_LAST_NAME_INDEX, ticket.getPassengerLastName());
		statement.setString(ADD_TICKET_QUERY_PASSPORT_NUMBER_INDEX, ticket.getPassengerPassportNumber());
		statement.setInt(ADD_TICKET_QUERY_LUGGAGE_QUANTITY_INDEX, ticket.getLuggageQuantity());
		statement.setBigDecimal(ADD_TICKET_QUERY_LUGGAGE_PRICE_INDEX, ticket.getOverweightLuggagePrice());
		statement.setBoolean(ADD_TICKET_QUERY_PRIMARY_BOARDING_INDEX, ticket.isPrimaryBoargingRight());
		statement.setInt(ADD_TICKET_QUERY_FLIGHT_ID_INDEX, ticket.getFlight().getId());
	}

	private void prepareUpdateUserBalanceStatement(PreparedStatement statemetn, String email, BigDecimal price)
			throws SQLException {
		statemetn.setString(UPDATE_USER_BALANCE_EMAIL_INDEX, email);
		statemetn.setBigDecimal(UPDATE_USER_BALANCE_BALANCE_INDEX, price);
	}

	private void prepareUpdateAirlinesBalanceStatement(PreparedStatement statement, BigDecimal price)
			throws SQLException {
		statement.setBigDecimal(UPDATE_AIRPORT_BALANCE_BALANCE_INDEX, price);
	}

	private void prepareAddTransactionStatement(PreparedStatement statement, Ticket ticket, BigDecimal price)
			throws SQLException {
		statement.setString(INSERT_TRANSACTION_FROM_INDEX, ticket.getEmail());
		statement.setTimestamp(INSERT_TRANSACTION_DATE_INDEX,
				new Timestamp(ticket.getPurchaseDate().getTimeInMillis()));
		statement.setBigDecimal(INSERT_TRANSACTION_AMOUNT_INDEX, price);
		statement.setLong(INSERT_TRANSACTION_TISKET_INDEX, ticket.getNumber());
	}

	private Ticket buildTicket(ResultSet resultSet, String email) throws SQLException {
		TicketBuilder builder = new TicketBuilder();
		builder.setEmail(email);
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
}