package by.training.karpilovich.lowcost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.lowcost.connection.ConnectionPool;
import by.training.karpilovich.lowcost.dao.PlaceCoefficientDAO;
import by.training.karpilovich.lowcost.entity.PlaceCoefficient;
import by.training.karpilovich.lowcost.exception.ConnectionPoolException;
import by.training.karpilovich.lowcost.exception.DAOException;
import by.training.karpilovich.lowcost.util.message.MessageType;

public class PlaceCoefficientDAOImpl implements PlaceCoefficientDAO {

	private final ConnectionPool pool = ConnectionPool.getInstance();

	private static final String ADD_PLACE_COEFFICIENT_QUERY = "INSERT INTO "
			+ " place_coeff (`flight_id`, `from`, `to`, `coeff`) "
			+ " VALUES(?, ?, ?, ?)";

	private static final int ADD_COEFFICIENT_QUERY_FLIGHT_ID_INDEX = 1;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX = 2;
	private static final int ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX = 3;
	private static final int ADD_COEFFICIENT_QUERY_VALUE_INDEX = 4;

	private static final Logger LOGGER = LogManager.getLogger(PlaceCoefficientDAOImpl.class);
	
	private PlaceCoefficientDAOImpl() {
	}

	public static PlaceCoefficientDAOImpl getInstance() {
		return PlaceCoefficientDAOImplInstanceHolder.INSTATNCE;
	}
	
	@Override
	public void addCoefficient(int flightId, PlaceCoefficient coefficient) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PLACE_COEFFICIENT_QUERY);) {
			prepareAddPlaceCoefficientStatement(statement, coefficient, flightId);
			statement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding coefficients ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}

	@Override
	public void addCoefficients(int flightId, Set<PlaceCoefficient> coefficients) throws DAOException {
		try (Connection connection = pool.getConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PLACE_COEFFICIENT_QUERY);) {
			prepareAddPlaceCoefficientStatement(statement, coefficients, flightId);
			statement.executeBatch();
		} catch (SQLException | ConnectionPoolException e) {
			LOGGER.error("Error while adding coefficients ", e);
			throw new DAOException(MessageType.INTERNAL_ERROR.getMessage(), e);
		}
	}
	
	private static final class PlaceCoefficientDAOImplInstanceHolder {
		private static final PlaceCoefficientDAOImpl INSTATNCE = new PlaceCoefficientDAOImpl();
	}

	private void prepareAddPlaceCoefficientStatement(PreparedStatement statement, Set<PlaceCoefficient> coefficients,
			int flightId) throws SQLException {
		for (PlaceCoefficient coefficient : coefficients) {
			prepareAddPlaceCoefficientStatement(statement, coefficient, flightId);
			statement.addBatch();
		}
	}

	private void prepareAddPlaceCoefficientStatement(PreparedStatement statement, PlaceCoefficient coefficient,
			int flightId) throws SQLException {
		statement.setInt(ADD_COEFFICIENT_QUERY_FLIGHT_ID_INDEX, flightId);
		statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_FROM_INDEX, coefficient.getFrom());
		statement.setInt(ADD_COEFFICIENT_QUERY_BOUND_TO_INDEX, coefficient.getTo());
		statement.setBigDecimal(ADD_COEFFICIENT_QUERY_VALUE_INDEX, coefficient.getValue());
	}
}