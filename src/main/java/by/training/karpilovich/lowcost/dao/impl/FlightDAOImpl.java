package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.CityBuilder;
import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.builder.PlaneBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.City;
import by.training.karpilovich.lowcost.entity.DateCoefficient;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.entity.Plane;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.MessageType;

public class FlightDAOImpl implements FlightDAO {

	private static final String ADD_FLIGHT_QUERY = "INSERT INTO flight "
			+ " (number, date, plane_model, from_id, to_id, default_price, default_luggage_kg, available_places, primary_boarding_right_price, price_for_every_kg_overweight) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final int ADD_FLIGHT_QUERY_NUMBER_INDEX = 1;
	private static final int ADD_FLIGHT_QUERY_DATE_INDEX = 2;
	private static final int ADD_FLIGHT_QUERY_PLANE_MODEL_INDEX = 3;
	private static final int ADD_FLIGHT_QUERY_CITY_FROM_ID_INDEX = 4;
	private static final int ADD_FLIGHT_QUERY_CITY_TO_ID_INDEX = 5;
	private static final int ADD_FLIGHT_QUERY_DEFAULT_PRICE_INDEX = 6;
	private static final int ADD_FLIGHT_QUERY_DEFAULT_LUGGAGE_INDEX = 7;
	private static final int ADD_FLIGHT_QUERY_PLACE_QUANTITY_INDEX = 8;
	private static final int ADD_FLIGHT_QUERY_PRIMARY_BOARDING_PRICE_INDEX = 9;
	private static final int ADD_FLIGHT_QUERY_PRICE_FOR_OVERWEIGHT_INDEX = 10;

	private static final String ADD_DATE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " date_coeff (`flight_id`, `from`, `to`, `coeff`) " + " VALUES (LAST_INSERT_ID(), ?, ?, ?)";

	private static final String ADD_PLACE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " place_coeff (`flight_id`, `from`, `to`, `coeff`) " + " VALUES(LAST_INSERT_ID(), ?, ?, ?)";

	private static final int ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX = 1;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX = 2;
	private static final int ADD_COEFFICIENT_QUERY_VALUE_INDEX = 3;

	private static final String COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY = "SELECT count(number) " + " FROM flight "
			+ " GROUP BY number, date " + " HAVING number = ? AND DATE(date) <> ?";

	private static final int COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_NUMBER_INDEX = 1;
	private static final int COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_DATE_INDEX = 2;

	private static final int COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_RESULT_INDEX = 1;

	private static final String SELECT_ALL_FLIGHTS_QUERY = "SELECT "
			+ " flight.id AS id, number, date, default_price * dc.coeff * pc.coeff AS price, default_luggage_kg, available_places, "
			+ " primary_boarding_right_price, plane.model, plane.places_quantity, price_for_every_kg_overweight, "
			+ " city_from.id AS city_from_id, city_from.name  AS city_from_name, city_from.country_name AS country_from, "
			+ " city_to.id AS city_to_id, city_to.name AS city_to_name, city_to.country_name AS country_to"
			+ " FROM flight " + " JOIN city AS city_from ON flight.from_id = city_from.id "
			+ " JOIN city AS city_to ON flight.to_id = city_to.id " + " JOIN plane ON flight.plane_model = plane.model "
			+ " JOIN date_coeff AS dc ON flight.id = dc.flight_id AND CURDATE() BETWEEN dc.from and dc.to "
			+ " JOIN place_coeff AS pc ON flight.id = pc.flight_id AND available_places BETWEEN pc.from and pc.to "
			+ " ORDER BY flight.id";

	private static final String SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY = "SELECT "
			+ " flight.id AS id, number, date, default_price * dc.coeff * pc.coeff AS price, default_luggage_kg, available_places,"
			+ " primary_boarding_right_price, plane.model, plane.places_quantity, price_for_every_kg_overweight, "
			+ " city_from.id AS city_from_id, city_from.name  AS city_from_name, city_from.country_name AS country_from, "
			+ " city_to.id AS city_to_id, city_to.name AS city_to_name, city_to.country_name AS country_to"
			+ " FROM flight " + " JOIN city AS city_from ON flight.from_id = city_from.id "
			+ " JOIN city AS city_to ON flight.to_id = city_to.id "
			+ " JOIN plane ON flight.plane_model = plane.model  "
			+ " JOIN date_coeff AS dc ON flight.id = dc.flight_id AND CURDATE() BETWEEN dc.from and dc.to "
			+ " JOIN place_coeff AS pc ON flight.id = pc.flight_id AND available_places BETWEEN pc.from and pc.to "
			+ " WHERE city_from.id=? AND city_to.id=? AND Date(date)=? AND available_places>=?" + " ORDER BY flight.id";

	private static final int SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_FROM_INDEX = 1;
	private static final int SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_TO_INDEX = 2;
	private static final int SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_DATE_INDEX = 3;
	private static final int SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_QUANTITY_INDEX = 4;

	private static final String RESULT_SELECT_FLIGHT_FLIGHT_ID = "id";
	private static final String RESULT_SELECT_FLIGHT_FLIGHT_NUMBER = "number";
	private static final String RESULT_SELECT_FLIGHT_FLIGHT_DATE = "date";
	private static final String RESULT_SELECT_FLIGHT_PRICE = "price";
	private static final String RESULT_SELECT_FLIGHT_DEFAULT_LUGGAGE = "default_luggage_kg";
	private static final String RESULT_SELECT_FLIGHT_AVAILABLE_PLACES = "available_places";
	private static final String RESULT_SELECT_FLIGHT_PRIMARY_BOARDING_PRICE = "primary_boarding_right_price";
	private static final String RESULT_SELECT_FLIGHT_PRICE_FOR_OVERWEIGHT = "price_for_every_kg_overweight";
	private static final String RESULT_SELECT_FLIGHT_PLANE_MODEL = "model";
	private static final String RESULT_SELECT_FLIGHT_PLANE_PASSENGER_QUANTITY = "places_quantity";
	private static final String RESULT_SELECT_FLIGHT_CITY_FROM_ID = "city_from_id";
	private static final String RESULT_SELECT_FLIGHT_CITY_FROM_NAME = "city_from_name";
	private static final String RESULT_SELECT_FLIGHT_CITY_FROM_COUNTRY = "country_from";
	private static final String RESULT_SELECT_FLIGHT_CITY_TO_ID = "city_to_id";
	private static final String RESULT_SELECT_FLIGHT_CITY_TO_NAME = "city_to_name";
	private static final String RESULT_SELECT_FLIGHT_CITY_TO_COUNTRY = "country_to";

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
	public void add(Flight flight, SortedSet<DateCoefficient> dateCoefficients,
			SortedSet<PlaceCoefficient> placeCoefficients) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = null;
		try {
			connection = pool.getConnection();
			connection.setAutoCommit(false);
			try (PreparedStatement flightStatement = connection.prepareStatement(ADD_FLIGHT_QUERY);
					PreparedStatement addDateCoefficientStatement = connection
							.prepareStatement(ADD_DATE_COEFFICIENT_QUERY);
					PreparedStatement addPlaceCoefficientStatement = connection
							.prepareStatement(ADD_PLACE_COEFFICIENT_QUERY);) {
				prepareAddFlightStatement(flightStatement, flight);
				prepareAddDateCoefficientStatement(addDateCoefficientStatement, dateCoefficients);
				prepareAddPlaceCoefficientStatement(addPlaceCoefficientStatement, placeCoefficients);
				flightStatement.executeUpdate();
				addPlaceCoefficientStatement.executeBatch();
				addDateCoefficientStatement.executeBatch();
			}
			connection.commit();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a flight " + flight.toString(), e);
			rollback(connection);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage());
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public void update(Flight flight) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Flight flight) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Flight> getFlightsByFromToDateAndPassengerQuantity(City from, City to, Calendar date, int quantity)
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY);) {
			prepareSelectByFromToDateAndPassengerQuantityStatement(statement, from, to, date, quantity);
			try (ResultSet resultSet = statement.executeQuery()) {
				Set<Flight> flights = new HashSet<>();
				while (resultSet.next()) {
					flights.add(buildFlight(resultSet));
				}
				return flights;
			}
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting a flight by from, to, date and passenger quantity: from=" + from + " to="
					+ to + " date=" + date + "quantity=" + quantity, e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public int countFlightWithNumberAndDate(String number, Calendar date) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY);) {
			prepareCountFlightByNumberAndDateQuery(statement, number, date);
			try (ResultSet resultSet = statement.executeQuery();) {
				int count = 0;
				if (resultSet.next()) {
					count = resultSet.getInt(COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_RESULT_INDEX);
				}
				return count;
			}
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error(e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public List<Flight> getAllFlights() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FLIGHTS_QUERY);
				ResultSet resultSet = statement.executeQuery();) {
			List<Flight> flights = new ArrayList<>();
			while (resultSet.next()) {
				flights.add(buildFlight(resultSet));
			}
			return flights;
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting all flights ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	private void prepareCountFlightByNumberAndDateQuery(PreparedStatement statement, String number, Calendar date)
			throws SQLException {
		statement.setString(COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_NUMBER_INDEX, number);
		statement.setDate(COUNT_FLIGHT_BY_NUMBER_AND_DATE_QUERY_DATE_INDEX, new Date(date.getTimeInMillis()));
	}

	private void prepareAddDateCoefficientStatement(PreparedStatement statement, Set<DateCoefficient> coefficients)
			throws SQLException {
		for (DateCoefficient coefficient : coefficients) {
			statement.setDate(ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX,
					new Date(coefficient.getFrom().getTimeInMillis()));
			statement.setDate(ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX, new Date(coefficient.getTo().getTimeInMillis()));
			statement.setBigDecimal(ADD_COEFFICIENT_QUERY_VALUE_INDEX, coefficient.getValue());
			statement.addBatch();
		}
	}

	private void prepareAddPlaceCoefficientStatement(PreparedStatement statement, Set<PlaceCoefficient> coefficients)
			throws SQLException {
		for (PlaceCoefficient coefficient : coefficients) {
			statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX, coefficient.getFrom());
			statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX, coefficient.getTo());
			statement.setBigDecimal(ADD_COEFFICIENT_QUERY_VALUE_INDEX, coefficient.getValue());
			statement.addBatch();
		}
	}

	private void prepareAddFlightStatement(PreparedStatement statement, Flight flight) throws SQLException {
		statement.setString(ADD_FLIGHT_QUERY_NUMBER_INDEX, flight.getNumber());
		statement.setTimestamp(ADD_FLIGHT_QUERY_DATE_INDEX, new Timestamp(flight.getDate().getTimeInMillis()));
		statement.setString(ADD_FLIGHT_QUERY_PLANE_MODEL_INDEX, flight.getPlaneModel().getModel());
		statement.setInt(ADD_FLIGHT_QUERY_CITY_FROM_ID_INDEX, flight.getFrom().getId());
		statement.setInt(ADD_FLIGHT_QUERY_CITY_TO_ID_INDEX, flight.getTo().getId());
		statement.setBigDecimal(ADD_FLIGHT_QUERY_DEFAULT_PRICE_INDEX, flight.getPrice());
		statement.setInt(ADD_FLIGHT_QUERY_DEFAULT_LUGGAGE_INDEX, flight.getPermittedLuggageWeigth());
		statement.setInt(ADD_FLIGHT_QUERY_PLACE_QUANTITY_INDEX, flight.getPlaneModel().getPlaceQuantity());
		statement.setBigDecimal(ADD_FLIGHT_QUERY_PRIMARY_BOARDING_PRICE_INDEX, flight.getPrimaryBoardingPrice());
		statement.setBigDecimal(ADD_FLIGHT_QUERY_PRICE_FOR_OVERWEIGHT_INDEX, flight.getPriceForEveryKgOverweight());
	}

	private void prepareSelectByFromToDateAndPassengerQuantityStatement(PreparedStatement statement, City from, City to,
			Calendar date, int quantity) throws SQLException {
		statement.setInt(SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_FROM_INDEX, from.getId());
		statement.setInt(SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_TO_INDEX, to.getId());
		statement.setDate(SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_DATE_INDEX, new Date(date.getTimeInMillis()));
		statement.setInt(SELECT_FLIGHT_BY_FROM_TO_DATE_QUANTITY_QUANTITY_INDEX, quantity);
	}

	private Flight buildFlight(ResultSet resultSet) throws SQLException {
		FlightBuilder builder = new FlightBuilder();
		int flightId = resultSet.getInt(RESULT_SELECT_FLIGHT_FLIGHT_ID);
		builder.setId(flightId);
		builder.setFlightNumber(resultSet.getString(RESULT_SELECT_FLIGHT_FLIGHT_NUMBER));
		builder.setDate(buildDate(resultSet.getTimestamp(RESULT_SELECT_FLIGHT_FLIGHT_DATE)));
		builder.setPrice(resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_PRICE));
		builder.setPermittedLuggageWeight(resultSet.getInt(RESULT_SELECT_FLIGHT_DEFAULT_LUGGAGE));
		builder.setAvailablePlaceQuantity(resultSet.getInt(RESULT_SELECT_FLIGHT_AVAILABLE_PLACES));
		builder.setPrimaryBoardingPrice(resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_PRIMARY_BOARDING_PRICE));
		builder.setOverweightLuggagePrice(resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_PRICE_FOR_OVERWEIGHT));
		builder.setPlaneModel(buildPlane(resultSet));
		builder.setFrom(buildCity(resultSet, RESULT_SELECT_FLIGHT_CITY_FROM_ID, RESULT_SELECT_FLIGHT_CITY_FROM_NAME,
				RESULT_SELECT_FLIGHT_CITY_FROM_COUNTRY));
		builder.setTo(buildCity(resultSet, RESULT_SELECT_FLIGHT_CITY_TO_ID, RESULT_SELECT_FLIGHT_CITY_TO_NAME,
				RESULT_SELECT_FLIGHT_CITY_TO_COUNTRY));
		return builder.getFlight();
	}

	private Plane buildPlane(ResultSet resultSet) throws SQLException {
		PlaneBuilder builder = new PlaneBuilder();
		builder.setPlaneModel(resultSet.getString(RESULT_SELECT_FLIGHT_PLANE_MODEL));
		builder.setPlanePlaceQuantity(resultSet.getInt(RESULT_SELECT_FLIGHT_PLANE_PASSENGER_QUANTITY));
		return builder.getPlane();
	}

	private City buildCity(ResultSet resultSet, String idColumn, String nameColumn, String countryColumn)
			throws SQLException {
		CityBuilder builder = new CityBuilder();
		builder.setCityId(resultSet.getInt(idColumn));
		builder.setCityName(resultSet.getString(nameColumn));
		builder.setCityCountry(resultSet.getString(countryColumn));
		return builder.getCity();
	}

	private Calendar buildDate(Timestamp timestamp) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar;
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

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error while adding a flight and closing " + e);
			}
		}
	}
}