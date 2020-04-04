package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.builder.coefficient.LuggageCoefficientBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.entity.coefficient.AbstractCoefficient;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.MessageType;

public class FlightDAOImpl implements FlightDAO {

	private static final String ADD_FLIGHT_QUERY = "INSERT INTO flight "
			+ " (number, date, plane_model, from_id, to_id, default_price, default_luggage_kg, available_places) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final int ADD_FLIGHT_QUERY_NUMBER_INDEX = 1;
	private static final int ADD_FLIGHT_QUERY_DATE_INDEX = 2;
	private static final int ADD_FLIGHT_QUERY_PLANE_MODEL_INDEX = 3;
	private static final int ADD_FLIGHT_QUERY_CITY_FROM_ID_INDEX = 4;
	private static final int ADD_FLIGHT_QUERY_CITY_TO_ID_INDEX = 5;
	private static final int ADD_FLIGHT_QUERY_DEFAULT_PRICE_INDEX = 6;
	private static final int ADD_FLIGHT_QUERY_DEFAULT_LUGGAGE_INDEX = 7;
	private static final int ADD_FLIGHT_QUERY_PLACE_QUANTITY_INDEX = 8;

	private static final String ADD_PLACES_COEFFICIENT_QUERY = "INSERT INTO "
			+ " free_places_coeff(flight_id, free_places_from, free_places_to, coeff) " 
			+ " VALUES(LAST_INSERT_ID(), ?, ?, ?)";

	private static final String ADD_DATE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " date_coeff(flight_id, days_from, days_to, coeff) " 
			+ " VALUES(LAST_INSERT_ID(), ?, ?, ?)";

	private static final String ADD_LUGGAGE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " overweight_luggage_price(flight_id, weight_from, weight_to, price_for_every_kg_overweight) " 
			+ " VALUES(LAST_INSERT_ID(), ?, ?, ?)";

	private static final int ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX = 1;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX = 2;
	private static final int ADD_COEFFICIENT_QUERY_VALUE_INDEX = 3;

	private static final String SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY = "SELECT "
			+ " id, flight.number, "
			+ " default_price * date_coeff.coeff * places_coeff.coeff AS price, "
			+ " default_luggage_kg, available_places, model, places_quantity, "
			+ " lug_price.weight_from, lug_price.weight_to, lug_price.price_for_every_kg_overweight "
			+ " FROM flight "
			+ " JOIN plane "
				+ " ON flight.plane_model = plane.model  "
			+ " JOIN free_places_coeff AS places_coeff "
				+ " ON flight.id = places_coeff.flight_id "
				+ " AND available_places BETWEEN places_coeff.free_places_to AND places_coeff.free_places_from "
			+ " JOIN date_coeff AS date_coeff "
				+ " ON flight.id = date_coeff.flight_id "
				+ " AND TIMESTAMPDIFF(DAY, CURRENT_TIMESTAMP(), date) BETWEEN date_coeff.days_to AND date_coeff.days_from  "
			+ " LEFT JOIN overweight_luggage_price AS lug_price ON flight.id = lug_price.flight_id "
			+ " WHERE from_id=? AND  to_id=? AND date=? AND available_places >= ?"
			+ " ORDER BY flight.number, flight.date;";

	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_FROM_INDEX = 1;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_TO_INDEX = 2;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX = 3;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY_INDEX = 4;

	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_ID_INDEX = 1;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_NUMBER_INDEX = 2;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PRICE_INDEX = 3;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGGAGE_INDEX = 4;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACES_INDEX = 5;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_MODEL_INDEX = 6;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX = 7;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_FROM_INDEX = 8;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_TO_INDEX = 9;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_PRICE_INDEX = 10;

	private static final Logger LOGGER = LogManager.getLogger(FlightDAOImpl.class);
	
	private FlightDAOImpl() {
		
	}
	
	private static final class FlightDAOImplInstanceHolder {
		private static final FlightDAOImpl INSTANCE = new FlightDAOImpl();
	}
	
	public static FlightDAO getInstance() {
		return FlightDAOImplInstanceHolder.INSTANCE;
	}

	@Override
	public boolean add(Flight flight) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement flightStatement = null;
		try {
			connection = pool.getConnection();
			connection.setAutoCommit(false);
			flightStatement = prepareAddFlightStatement(connection, flight);
			flightStatement.executeUpdate();
			insertCoefficietns(connection, flight.getDateCoefficient(), ADD_DATE_COEFFICIENT_QUERY);
			insertCoefficietns(connection, flight.getPlaceCoefficient(), ADD_PLACES_COEFFICIENT_QUERY);
			insertCoefficietns(connection, flight.getLuggageCoefficient(), ADD_LUGGAGE_COEFFICIENT_QUERY);
			connection.commit();
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a flight " + flight.toString(), e);
			rollback(connection);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		} finally {
			closePreparedStatement(flightStatement);
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("Error while adding flight and setting setAutoCommit=true " + flight.toString(), e);
			}
			closeConnection(connection);
		}
	}

	@Override
	public boolean update(Flight flight) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Flight flight) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Flight> getFlightsByDateAndPassengerQuantityWithoutPlaceAndDateCoefficient(City from, City to, Calendar date, int quantity)
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareSelectByDateAndPassengerQuantityStatement(connection, from, to,
						date, quantity);
				ResultSet resultSet = statement.executeQuery();) {
			Set<Flight> flights = new HashSet<>();
			Flight flight;
			LOGGER.error("from=" + from.getId() + " to=" + to.getId()
					+ " date=" + date + "quantity=" + quantity);
			LOGGER.debug(resultSet.next());
			resultSet.previous();
			while (resultSet.next()) {
				flight = buildFlight(resultSet, from, to, date);
				addLuggageCoefficientsToFlight(resultSet, flight);
				flights.add(flight);
			}
			return flights;
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting a flight by date and passenger quantity: from=" + from + " to=" + to
					+ " date=" + date + "quantity=" + quantity, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private void insertCoefficietns(Connection connection, Set<? extends AbstractCoefficient> coefficients,
			String query) throws SQLException {
		PreparedStatement statement = prepareAddCoefficientStatement(connection, coefficients, query);
		statement.executeUpdate();
	}

	private PreparedStatement prepareAddFlightStatement(Connection connection, Flight flight) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(ADD_FLIGHT_QUERY);
		statement.setString(ADD_FLIGHT_QUERY_NUMBER_INDEX, flight.getNumber());
		statement.setTimestamp(ADD_FLIGHT_QUERY_DATE_INDEX, new Timestamp(flight.getDate().getTimeInMillis()));
		statement.setString(ADD_FLIGHT_QUERY_PLANE_MODEL_INDEX, flight.getPlaneModel().getModel());
		statement.setInt(ADD_FLIGHT_QUERY_CITY_FROM_ID_INDEX, flight.getFrom().getId());
		statement.setInt(ADD_FLIGHT_QUERY_CITY_TO_ID_INDEX, flight.getTo().getId());
		statement.setBigDecimal(ADD_FLIGHT_QUERY_DEFAULT_PRICE_INDEX, flight.getPrice());
		statement.setInt(ADD_FLIGHT_QUERY_DEFAULT_LUGGAGE_INDEX, flight.getPermittedLuggageWeigth());
		statement.setInt(ADD_FLIGHT_QUERY_PLACE_QUANTITY_INDEX, flight.getPlaneModel().getPlaceQuantity());
		return statement;
	}

	private PreparedStatement prepareAddCoefficientStatement(Connection connection,
			Set<? extends AbstractCoefficient> coefficients, String query) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		for (AbstractCoefficient coefficient : coefficients) {
			statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX, coefficient.getBoundFrom());
			statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX, coefficient.getBoundTo());
			statement.setBigDecimal(ADD_COEFFICIENT_QUERY_VALUE_INDEX, coefficient.getValue());
			statement.addBatch();
		}
		return statement;
	}

	private PreparedStatement prepareSelectByDateAndPassengerQuantityStatement(Connection connection, City from,
			City to, Calendar date, int quantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY);
		statement.setInt(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_FROM_INDEX, from.getId());
		statement.setInt(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_TO_INDEX, to.getId());
		statement.setTimestamp(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX, new Timestamp(date.getTimeInMillis()), new GregorianCalendar());
		statement.setInt(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY_INDEX, quantity);
		return statement;
	}

	private Flight buildFlight(ResultSet resultSet, City from, City to, Calendar date) throws SQLException {
		FlightBuilder builder = new FlightBuilder();
		builder.setId(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_ID_INDEX));
		builder.setAvailablePlaceQuantity(
				resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX));
		builder.setFrom(from);
		builder.setTo(to);
		builder.setFlightNumber(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_NUMBER_INDEX));
		builder.setDate(date);
		builder.setPrice(resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PRICE_INDEX));
		builder.setPermittedLuggageWeight(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGGAGE_INDEX));
		builder.setAvailablePlaceQuantity(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACES_INDEX));
		builder.setPlaneModel(
				new Plane(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_MODEL_INDEX),
						resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX)));
		return builder.getFlight();
	}

	private void addLuggageCoefficientsToFlight(ResultSet resultSet, Flight flight) throws SQLException {
		LuggageCoefficientBuilder builder;
		int id = flight.getId();
		while (resultSet.next()
				&& (id = resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_ID_INDEX)) == flight.getId()) {
			builder = new LuggageCoefficientBuilder();
			builder.setFlightId(id);
			builder.setBoundFrom(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_FROM_INDEX));
			builder.setBoundTo(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_TO_INDEX));
			builder.setValue(
					resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGG_WEIGHT_PRICE_INDEX));
			flight.addLuggageCoefficient(builder.getCoefficient());
		}
		if (id != flight.getId()) {
			resultSet.previous();
		}
	}

	private void rollback(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.error("Error while adding a flight and rolling back " + e1);
			}
		}
	}

	private void closePreparedStatement(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.error("Error while adding a flight and closing prepared statrement" + e);
			}
		}
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error while adding a flight and closing " + e);
			}
		}
	}

}
