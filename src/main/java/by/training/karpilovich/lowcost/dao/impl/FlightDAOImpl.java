package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.builder.FlightBuilder;
import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.FlightDAO;
import by.training.karpilovich.lowcost.entity.Flight;
import by.training.karpilovich.lowcost.entity.PlaneModel;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DaoException;
import by.training.karpilovich.lowcost.util.MessageType;

public class FlightDAOImpl implements FlightDAO {

	private static final String ADD_QUERY = "INSERT INTO flight "
			+ " VALUES(number, date, plane_model, from, to, default_price, default_luggage_kg, available_places) "
			+ " ?, ?, "
			+ " (SELECT id FROM city WHERE ?=city.name),"
			+ " (SELECT id FROM city WHERE ?=city.name), ?, ?, ?, ?";

	private static final int ADD_QUERY_NUMBER_INDEX = 1;
	private static final int ADD_QUERY_DATE_INDEX = 2;
	private static final int ADD_QUERY_PLANE_MODEL_INDEX = 3;
	private static final int ADD_QUERY_COUNTRY_FROM_INDEX = 4;
	private static final int ADD_QUERY_COUNTRY_TO_INDEX = 5;
	private static final int ADD_QUERY_DEFAULT_PRICE_INDEX = 6;
	private static final int ADD_QUERY_DEFAULT_LUGGAGE_INDEX = 7;
	private static final int ADD_QUERY_PLACE_QUANTITY_INDEX = 8;

	private static final String SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY = "SELECT "
			+ " flight.number, flight.date, city_from.name, city_to.name, "
			+ " default_price * date_coeff.coeff * places_coeff.coeff AS price, "
			+ " default_luggage_kg, available_places, model, available_places, "
			+ " lug_price.weight_from, lug_price.weight_to, lug_price.price_for_every_kg_overweight "
			+ " FROM flight "
			+ " JOIN plane ON flight.plane_model = plane.model "
			+ " JOIN city AS city_from ON flight.from_id = city_from.id "
			+ " JOIN city AS city_to ON flight.to_id = city_to.id "
			+ " JOIN free_places_coeff AS places_coeff "
				+ " ON flight.number = places_coeff.flight_number "
				+ " AND flight.date = places_coeff.flight_date"
				+ " AND available_places <= places_coeff.free_places_from "
				+ " AND available_places > places_coeff.free_places_to "
			+ "JOIN date_coeff AS date_coeff "
				+ " ON flight.number = date_coeff.flight_number "
				+ " AND flight.date = date_coeff.flight_date"
				+ " AND TIMESTAMPDIFF(DAY, CURRENT_TIMESTAMP(), date) <= date_coeff.days_from "
				+ " AND TIMESTAMPDIFF(DAY, CURRENT_TIMESTAMP(), date) > date_coeff.days_to  "
			+ " RIGHT JOIN overweight_luggage_price AS lug_price ON flight.number = lug_price.flight_number "
			+ "	WHERE city_from.name=? AND  city_to.name=? AND date>=? AND available_places >= ?";


	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_FROM_INDEX = 1;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_TO_INDEX = 2;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX = 3;
	private static final int SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY_INDEX = 4;
	

	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_NUMBER_INDEX = 1;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX = 2;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_COUNTRY_FROM_INDEX = 3;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_COUNTRY_TO_INDEX = 4;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PRICE_INDEX = 5;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGGAGE_INDEX = 6;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACES_INDEX = 7;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_MODEL_INDEX = 8;
	private static final int RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX = 9;

	private static final Logger LOGGER = LogManager.getLogger(FlightDAOImpl.class);

	@Override
	public boolean add(Flight flight) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareAddStatement(connection, flight);) {
			int result = statement.executeUpdate();
			return result == 1;
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding a flight " + flight.toString(), e);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType());
		}
	}

	@Override
	public boolean update(Flight flight) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Flight flight) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Flight> getFlightsByDateAndPassengerQuantity(String countryFrom, String countryTo, Calendar date,
			int quantity) throws DaoException {
		ConnectionPool pool = ConnectionPool.getInstance();
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = prepareSelectByDateAndPassengerQuantityStatement(connection, countryFrom,
						countryTo, date, quantity);
				ResultSet resultSet = statement.executeQuery();) {
			List<Flight> flights = new ArrayList<>();
			while (resultSet.next()) {
				flights.add(buildFlight(resultSet));
			}
			return flights;
		} catch (ConnectionPoolException | SQLException e) {
			LOGGER.error("Error while getting a flight by date and passenger quantity: countryFrom=" + countryFrom
					+ " countryTo=" + countryTo + " date=" + date + "quantity=" + quantity);
			throw new DaoException(MessageType.INTERNAL_ERROR.getType(), e);
		}
	}

	private PreparedStatement prepareAddStatement(Connection connection, Flight flight) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(ADD_QUERY);
		statement.setString(ADD_QUERY_NUMBER_INDEX, flight.getNumber());
		statement.setDate(ADD_QUERY_DATE_INDEX, (Date) flight.getDate().getTime());
		statement.setString(ADD_QUERY_PLANE_MODEL_INDEX, flight.getPlaneModel().getName());
		statement.setString(ADD_QUERY_COUNTRY_FROM_INDEX, flight.getFrom());
		statement.setString(ADD_QUERY_COUNTRY_TO_INDEX, flight.getTo());
		statement.setBigDecimal(ADD_QUERY_DEFAULT_PRICE_INDEX, flight.getPrice());
		statement.setInt(ADD_QUERY_DEFAULT_LUGGAGE_INDEX, flight.getPermittedLuggageWeigth());
		statement.setInt(ADD_QUERY_PLACE_QUANTITY_INDEX, flight.getPlaneModel().getPlaceQuantity());
		return statement;
	}

	private PreparedStatement prepareSelectByDateAndPassengerQuantityStatement(Connection connection, String from,
			String to, Calendar date, int quantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY);
		statement.setString(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_FROM_INDEX, from);
		statement.setString(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_TO_INDEX, to);
		statement.setDate(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX, (Date) date.getTime());
		statement.setInt(SELECT_FLIGHT_BY_DATE_AND_PASSENGER_QUANTITY_INDEX, quantity);
		return statement;
	}

	private Flight buildFlight(ResultSet resultSet) throws SQLException {
		FlightBuilder builder = new FlightBuilder();
		builder.setAvailablePlaceQuantity(
				resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX));
		builder.setFrom(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_COUNTRY_FROM_INDEX));
		builder.setTo(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_COUNTRY_TO_INDEX));
		builder.setFlightNumber(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_NUMBER_INDEX));
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(resultSet.getDate(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_DATE_INDEX));
		builder.setDate(date);
		builder.setPrice(resultSet.getBigDecimal(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PRICE_INDEX));
		builder.setPermittedLuggageWeight(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_LUGGAGE_INDEX));

		builder.setAvailablePlaceQuantity(resultSet.getInt(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACES_INDEX));
		PlaneModel model = new PlaneModel();
		model.setName(resultSet.getString(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_MODEL_INDEX));
		model.setPlaceQuantity(RESULT_SELECT_FLIGHT_BY_DATE_AND_PASSENGER_PLACE_QUANTITY_INDEX);
		builder.setPlaneModel(model);
		return builder.getFlight();
	}

}
